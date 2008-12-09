# To change this template, choose Tools | Templates
# and open the template in the editor.

require "rubygems"
require "java"
require "ActiveRecord"
require "thread"

module TopCoder
  module Acts
    module IdGenerator
     
      class IdGenerator
        attr_accessor :sequence_name
        attr_reader :id_sequence
       
        def initialize(id_sequence, sequence_name = nil)
          @id_sequence = id_sequence
          @sequence_name = sequence_name
          @mutex = Mutex.new
          @next_id = 0
          @id_left = -1

        end

        def next_id
          @mutex.synchronize do
            if @id_left <= 0
              load_next_block
            end
            @id_left -= 1
            @next_id += 1
          end
          @next_id - 1
        end

        private
        def load_next_block
          record  = @id_sequence.find(:first, :conditions => [ "name = ? AND  exhausted = 0", @sequence_name ])
          @next_id = record.next_block_start
          @id_left = record.block_size
          record.next_block_start += record.block_size
          record.save
        end
      end
     
      def self.included(base)
        base.extend ClassMethods
      end

      module ClassMethods
        def acts_as_id_generator (attributes = nil)
          table_name = attributes[:table] ? attributes[:table] : "id_sequences"


          id_sequence_class = Class.new(ActiveRecord::Base)
          id_sequence_class.set_table_name(table_name)
          id_sequence_class.set_primary_key("name")

          ActiveRecord::Base.send(:before_validation_on_create, :generate_table_driven_id)


          ActiveRecord::Base.class_eval do
            @@id_generator =IdGenerator.new(id_sequence_class,  attributes[:sequence])
           
            private
            def generate_table_driven_id()
              @@id_generator.sequence_name ||= self.table_name + "_seq"
              self.id = @@id_generator.next_id
              puts "generate_table_driven_id"
            end
           
          end
        end
      
      end

    end
  end
end

ActiveRecord::Base.send(:include, TopCoder::Acts::IdGenerator)

ActiveRecord::Base.logger = Logger.new(STDOUT)
ActiveRecord::Base.establish_connection(
  :adapter=>"jdbcmysql",
  :database=>"acts_as_id_generator_development",
  :username=>"root",
  :password=>"root",
  :host=>"localhost"
)

=begin
class CreateCowsTable < ActiveRecord::Migration
    def self.up
        create_table :cows do |t|
            t.string :name
            t.string :breed
            t.datetime :born_on
            t.boolean :milkable

            t.timestamps
        end
    end

    def self.down
        drop_table :cows
    end
end
CreateCowsTable.up
=end

class Cow < ActiveRecord::Base
  set_table_name "Cows"
  acts_as_id_generator :sequence=>"first", :table=>"id_sequences"
end



20.times  do |i|
  cow = Cow.new
  cow.name = "cow" + i.to_s
  cow.save
end

