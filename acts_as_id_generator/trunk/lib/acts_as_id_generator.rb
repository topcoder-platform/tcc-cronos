# Copyright (c) 2008, TopCoder, Inc. All rights reserved.

require "rubygems"
require "active_record"

# This module provide a Rails plugin that will allow ActiveRecord models to
# easily generate their primary key ids from legacy Id Generator sequences.
#
# Thread-safety: This component is not completely thread safe.
# But it can be used in thread-safe way.
# The IdGenerator and Errors have mutable properties, but they will not
# be set in multiple threads.
# The method next_id is synchronized which is the only method used
# in multiple threads, so this component can be used in thread-safe way.
#
# @author dmks, TCSDEVELOPER
# @version 1.0
module TopCoder
  module Acts
    module IdGenerator

      # This exception will be thrown if the specified sequence is not found.
      #
      # Thread-safety: This exception is mutable and not thread-safe.
      class MissingSequenceError < RuntimeError
      end

      # This exception will be thrown if the specified sequence is exhausted.
      #
      # Thread-safety: This exception is mutable and not thread-safe.
      class ExhaustedSequenceError < RuntimeError
      end

      # Once the sequence is greater or equals than this value, that means
      # the sequence has exhausted.
      EXHAUSTED_SEQUENCE = 10 ** 12

      # This class is the class to generate table driven id.
      #
      # The table has following structure:
      # CREATE TABLE id_sequences (
      # name VARCHAR(255) PRIMARY KEY,
      # next_block_start DECIMAL(12,0) NOT NULL,
      # block_size DECIMAL(10,0) NOT NULL,
      # exhausted DECIMAL(1,0) NOT NULL DEFAULT 0
      # );
      # CREATE UNIQUE INDEX 166_166 ON id_sequences(name);
      # However, the table name can be arbitrary, it is table_name parameter
      # of acts_as_id_generator method.
      #
      # It uses a dynamic class which extends ActiveRecord::Base to read/modify
      # sequence table in database.
      # It uses mutex to lock the next_id method, it also uses
      # ActiveRecord::Locking::Pessimistic to lock  the appropriate sequence row.
      #
      # Thread-safety: The sequence_name is mutable. But the next_id method
      # is synchronized, as only the next_id will be called in multiple threads,
      # we can treat this class as thread-safe.
      class IdGenerator

        # Gets the name of sequence (the key of sequence row in the table).
        # Can't be nil or empty.
        attr_reader :sequence_name

        # Gets the dynamic class which extends ActiveRecord::Base to read/modify
        # sequence table in database.
        # Can not be nil.
        attr_reader :id_sequence_class

        # Sets the name of sequence (the key of sequence row in the table).
        # Can't be nil or empty.
        #
        # @param sequence_name the sequence name be set
        # @throws ArguemntError if sequence name is nil or empty string
        def sequence_name=(sequence_name)
          check_string sequence_name, "sequence_name", true
          @sequence_name = sequence_name
        end

        # The constructor of IdGenerator.
        # Constructs IdGenerator with given id_sequence_class and sequence_name.
        #
        # @param id_sequence_class the dynamic class which extends the
        #            ActiveRecord::Base, can't be nil
        # @param sequence_name the sequence_name,can be nil, if it is not specified,
        #            default to nil
        # @throws ArgumentError if the id_sequence_class is nil
        def initialize(id_sequence_class, sequence_name = nil)
          # checkes the arguments
          raise ArgumentError.new("id_sequence_class should not be nil.") if id_sequence_class.nil?
          unless extendActiveRecordBase id_sequence_class
            raise ArgumentError.new "id_sequence_class should extend from ActiveRecord."
          end
          check_string sequence_name, "sequence_name", false
          # initializes instance variables
          @id_sequence_class = id_sequence_class
          @sequence_name = sequence_name
          @mutex = Mutex.new
          @next_id = 0
          @id_left = -1
        end

        # Returns the next table driven id.
        #
        # @return the next table driven id
        # @throws ActiveRecordError if the table of sequence doesn't exist in
        #             the datbase
        # @throws MissingSequenceError if the specified sequence name doesn't exist
        #             in the sequence table
        # @throws ExhaustedSequenceError if the specified sequence is exhausted
        def next_id
          # lockes this method
          @mutex.synchronize do
            # if there is no ids left for current block, load the next block
            # from database
            load_next_block unless @id_left > 0
            # if current id is greater or equal than 10 ** 12, that means
            # sequence has exhausted already, we should throw an exception
            if @next_id >= EXHAUSTED_SEQUENCE
              raise ExhaustedSequenceError.new("id sequence #{@sequence_name} exhausted")
            end
            @id_left -= 1
            @next_id += 1
          end
          @next_id - 1
        end

        # Loads the next id block from database.
        #
        # @throws ActiveRecordError if the table of sequence doesn't exist in
        #             the database
        # @throws MissingSequenceError if the specified sequence name doesn't exist
        #             in the sequence table
        # @throws ExhaustedSequenceError if the specified sequence is exhausted
        private
        def load_next_block
          # throws an exception if the
          @id_sequence_class.find :first

          # throws an exception if the sequence name doesn't exist
          if @id_sequence_class.find(:first, :conditions => { :name => @sequence_name }).nil?
            raise MissingSequenceError.new("the sequence #{@sequence_name} doesn't exist")
          end

          record = nil
          @id_sequence_class.transaction do
            # gets the next sequence row from database, we also lock the sequence row in database
            record  = @id_sequence_class.find:first,
              :conditions => { :name => @sequence_name, :exhausted => 0 },
              :lock => true
          end
          # if the sequence has exhausted already, throws an exception
          raise ExhaustedSequenceError.new("id sequence #{@sequence_name} exhausted") if record.nil?

          @next_id = record.next_block_start
          @id_left = record.block_size
          # update the sequence row
          record.next_block_start += record.block_size

          # if the sequence is going to be exhausted, we should record this
          # information into database
          if record.next_block_start >= EXHAUSTED_SEQUENCE
            record.exhausted = 1
            record.next_block_start = -1
          end
          record.save
        end

        # Helper method to check a string value.
        # The value should not be nil(if check_nil is true),
        # and should not be empty string.
        #
        # @param value the string value to check
        # @param name the variable name
        # @param check_nil indicates whether we check nil or not
        # @throws ArgumentError if value is nil(check_nil is true) or value is
        # empty string
        def check_string(value, name, check_nil)
          raise ArgumentError.new("#{name} can not set to nil.") if value.nil? and check_nil
          unless value.nil?
            if value.strip.empty?
              raise ArgumentError.new("#{name} can not set to empty string.")
            end
          end
        end

        # Checks whether id_sequence_class is instance of Class and extends from
        # ActiveRecord.
        #
        # @param id_sequence_class the dynamic class should extend from
        #            ActiveRecord::Base
        # @return true if id_sequence_class is a Class which extends from
        #             ActiveRecord::Base
        def extendActiveRecordBase(id_sequence_class)
          if id_sequence_class.instance_of? Class
            sequence_class = id_sequence_class
            while sequence_class != Object
              if sequence_class.superclass == ActiveRecord::Base
                return true
              end
              sequence_class = sequence_class.superclass
            end
          end
          false
        end
      end

      #This is a function of TopCoder::Acts::IdGenerator module.
      #It will be called when the ActiveRecord::Base extends TopCoder::Acts::IdGenerator module.
      #It will let the caller extend the ClassMethods module
      def self.included(base)
        base.extend ClassMethods
      end

      #This is the module will be extended by the ActiveRecord::Base,
      #so that the acts_as_id_generator method will be become a class level
      #method of  ActiveRecord::Base.
      module ClassMethods

        # This method will replace the built-in id generation of ActiveRecord::Base
        # with table driven id generation.
        #
        # @param attributes  it accept two keys:
        #            sequence -  the sequence is the sequence name to generate the id,
        #                        can be nil, default as table_name + "_seq",
        #                        table_name here is the table of record,
        #                        not the table to generate to id
        #            table - the table to generate the id, can be nil,
        #                    default as "id_sequences"
        def acts_as_id_generator (attributes = {})
          # gets the table_name, if it is not speficied in attributes, use the
          # default value
          table_name = attributes[:table] ? attributes[:table] : "id_sequences"
          # gets the sequence name, if it is not specified in attributes, use nil
          sequence_name = attributes[:sequence]

          # creates the id sequence class will be used in id generator
          id_sequence_class = Class.new ActiveRecord::Base
          id_sequence_class.set_table_name table_name
          # the primary key should be name
          id_sequence_class.set_primary_key "name"

          # adds method for the class which calles acts_as_id_generator method
          class_eval <<-EOV
            # this makes rails call generate_table_driven_id to assign the record
            # an id before saving it
            before_validation_on_create :generate_table_driven_id

            # adds an class variable, that's the id generator will be used to
            # generating ids
            @@id_generator = IdGenerator.new id_sequence_class,  sequence_name

            # Generates an id value and assigns it to record being saved.
            private
            def generate_table_driven_id
              # if the sequence name does not specified, use the default value
              @@id_generator.sequence_name ||= self.class.table_name + "_seq"
              # assigns an id
              self.id = @@id_generator.next_id
            end
          EOV
        end
      end
    end
  end
end

