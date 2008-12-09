# To change this template, choose Tools | Templates
# and open the template in the editor.

$LOAD_PATH.unshift(File.expand_path("#{File.dirname(__FILE__)}"))
require File.dirname(__FILE__) + '/../lib/acts_as_id_generator'
require "create_cows_table"
require "spec_helper"

#include Helper

# prepares the db connection, init the plugin, etc...
# defined in Helper module
#init

describe TopCoder::Acts::IdGenerator do

  before(:all) do
    ActiveRecord::Base.send(:include, TopCoder::Acts::IdGenerator)
    ActiveRecord::Base.logger = Logger.new(STDOUT)
    MigrationTestTables.up
  end

  before(:each) do
    CreateCowsTable.up
    @id_sequences = Class.new ActiveRecord::Base
    @id_sequences.set_table_name "id_sequences"
    @id_sequences.set_primary_key "name"
    foos_seq = @id_sequences.new
    foos_seq.name = "cows_seq"
    foos_seq.next_block_start = 1
    foos_seq.block_size = 3
    foos_seq.save

    @id_sequences_a = Class.new ActiveRecord::Base
    @id_sequences_a.set_table_name "id_sequences_a"
    @id_sequences_a.set_primary_key "name"
    foos_seq = @id_sequences_a.new
    foos_seq.name = "cows_seq_a"
    foos_seq.next_block_start = 1
    foos_seq.block_size = 3
    foos_seq.save
  end

  after(:each) do
    @id_sequences.delete_all("name"=>"cows_seq")
    @id_sequences_a.delete_all("name"=>"cows_seq_a")
    CreateCowsTable.down
  end

  after(:all) do
    MigrationTestTables.down
  end

  it "acts as id generator with default sequence name and table name" do
    cows = Class.new ActiveRecord::Base
    cows.set_table_name "cows"
    cows.acts_as_id_generator

    cow = cows.new
    cow.name = 'cow-1'
    cow.save

    id_seq = @id_sequences.find(:first, :conditions => ["name = ? AND  exhausted = 0", "cows_seq"])
    id_seq.next_block_start.should == 4
  end

  it "acts as id generator with specified sequence name and table name" do
    cows = Class.new ActiveRecord::Base
    cows.set_table_name "cows"
    cows.acts_as_id_generator :sequence=>"cows_seq_a", :table=>"id_sequences_a"

    cow = cows.new
    cow.name = 'cow-2'
    cow.save

    id_seq = @id_sequences_a.find(:first, :conditions => ["name = ? AND  exhausted = 0", "cows_seq_a"])
    id_seq.next_block_start.should == 4
  end
end

