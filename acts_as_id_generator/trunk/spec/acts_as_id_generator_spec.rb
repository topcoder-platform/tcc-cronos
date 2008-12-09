# Copyright (c) 2008, TopCoder, Inc. All rights reserved.

$LOAD_PATH.unshift(File.expand_path("#{File.dirname(__FILE__)}"))
require File.dirname(__FILE__) + '/../lib/acts_as_id_generator'
require 'spec_helper'

# This spec contains accuracy/failure tests for this component.
#
# @author TCSDEVELOPER
# @version 1.0

# mix-in Helper module
include Helper

# prepares the db connection, init the plugin, etc...
# defined in Helper module
init

# Accuracy/Failure tests for this component.
describe TopCoder::Acts::IdGenerator do
  # creates tables
  before(:all) do
    MigrationTestTables.up
  end

  # inserts neccessary sequences
  before(:each) do
    # insert a "foos_seq" sequence
    @id_sequences = Class.new ActiveRecord::Base
    @id_sequences.set_table_name "id_sequences"
    @id_sequences.set_primary_key "name"
    foos_seq = @id_sequences.new
    foos_seq.name = "foos_seq"
    foos_seq.next_block_start = 1
    foos_seq.block_size = 3
    foos_seq.save

    # insert a "foos_seq_a" sequence
    @id_sequences_a = Class.new ActiveRecord::Base
    @id_sequences_a.set_table_name "id_sequences_a"
    @id_sequences_a.set_primary_key "name"
    foos_seq_a = @id_sequences_a.new
    foos_seq_a.name = "foos_seq_a"
    foos_seq_a.next_block_start = 1
    foos_seq_a.block_size = 3
    foos_seq_a.save
  end

  # removes all added rows
  after(:each) do
    # removes all rows in "foos" table
    foos = Class.new ActiveRecord::Base
    foos.set_table_name "foos"
    foos.delete_all
    # removes all rows in "users" table
    users = Class.new ActiveRecord::Base
    users.set_table_name "users"
    users.delete_all
    # deletes sequences
    @id_sequences.delete_all
    @id_sequences_a.delete_all
  end

  # removes the created tables
  after(:all) do
    MigrationTestTables.down
  end

  # Failure test for IdGenerator#initialize, the passed id_sequence_class
  # is nil.
  # Expect ArgumentError.
  it "should throw ArgumentError to instantiate IdGenerator with id_sequence_class set to nil" do
    begin
      TopCoder::Acts::IdGenerator::IdGenerator.new(nil)
      raise RuntimeError
    rescue ArgumentError
      # expected
    end
  end

  # Accuracy test for IdGenerator#initialize.
  # Instance should be created and properly initialized.
  it "should be ok to instantiate IdGenerator with id_sequence_class set to @id_sequences" do
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences)
    gen.id_sequence_class.should == @id_sequences
    gen.sequence_name.should == nil
  end

  # Accuracy test for IdGenerator#initialize.
  # Instance should be created and properly initialized.
  it "should be ok to instantiate IdGenerator with both argument are valid" do
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences, "foos_seq")
    gen.id_sequence_class.should == @id_sequences
    gen.sequence_name.should == "foos_seq"
  end

  # Failure test for IdGenerator#next_id.
  # The table name doesn't exist in database,
  # expect ActiveRecord::ActiveRecordError.
  it "should throw ActiveRecordError when the table name doens't exist in database" do
    id_sequence_class = Class.new ActiveRecord::Base
    id_sequence_class.set_table_name "not_exist_sequences_table"
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(id_sequence_class, "foos_seq")

    begin
      gen.next_id
      raise RuntimeError
    rescue ActiveRecord::ActiveRecordError
      #expected
    end
  end

  # Failure test for IdGenerator#next_id.
  # The sequence name doesn't exist in the given table,
  # expect TopCoder::Acts::IdGenerator::MissingSequenceError.
  it "should throw MissingSequenceError when the sequence name doens't exist in the given table" do
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences, "not_exist_sequence_name")

    begin
      gen.next_id
      raise RuntimeError
    rescue TopCoder::Acts::IdGenerator::MissingSequenceError
      #expected
    end
  end

  # Failure test for IdGenerator#next_id.
  # The sequence is exhausted,
  # expect TopCoder::Acts::IdGenerator::ExhaustedSequenceError.
  it "should throw ExhaustedSequenceError when the sequence is exhausted when we generate a new id" do
    foos_seq = @id_sequences.find :first, :conditions => { :name => "foos_seq" }
    foos_seq.next_block_start = 10 ** 12 - 1
    foos_seq.save

    # not exhausted yet
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences, "foos_seq")
    gen.next_id.should == 10 ** 12 - 1

    begin
      gen.next_id
      raise RuntimeError
    rescue TopCoder::Acts::IdGenerator::ExhaustedSequenceError
      # expected
    end
  end

  # Failure test for IdGenerator#next_id.
  # The sequence is exhausted,
  # expect TopCoder::Acts::IdGenerator::ExhaustedSequenceError.
  it "should throw ExhaustedSequenceError when  the sequence is exhausted when we fetch it from database" do
    foos_seq = @id_sequences.find :first, :conditions => { :name => "foos_seq" }
    foos_seq.next_block_start = 10 ** 12 - 3
    foos_seq.save

    # not exhausted yet
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences, "foos_seq")
    gen.next_id.should == 10 ** 12 - 3
    gen.next_id.should == 10 ** 12 - 2
    gen.next_id.should == 10 ** 12 - 1

    begin
      gen.next_id
      raise RuntimeError
    rescue TopCoder::Acts::IdGenerator::ExhaustedSequenceError
      # expected
    end
  end

  # Accurcy test for IdGenerator#next_id.
  # The ids should be generated.
  it "should generate the expected ids by calling next_id" do
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences, "foos_seq")
    20.times do |t|
      gen.next_id.should == t + 1
    end
  end

  # Failure test for IdGenerator#sequence_name.
  # The value to set is nil,
  # expect ArgumentError.
  it "should be successful to set sequence_name to nil" do
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences)
    begin
      gen.sequence_name = nil
      raise RunttimeError
    rescue ArgumentError
      # expected
    end
  end

  # Failure test for IdGenerator#sequence_name.
  # The value to set is empty string,
  # expect ArgumentError.
  it "should throw ArgumentError to set sequence_name to empty string" do
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences)
    begin
      gen.sequence_name = " \t "
      raise RunttimeError
    rescue ArgumentError
      # expected
    end
  end

  # Accurcy test for IdGenerator#sequence_name.
  # The value to set is valid.
  it "should be ok to set sequence_name to valid value" do
    gen = TopCoder::Acts::IdGenerator::IdGenerator.new(@id_sequences)
    gen.sequence_name = "valid value"
    gen.sequence_name.should == "valid value"
  end

  # Failure test for ActiveRecord::Base#generate_table_driven_id.
  # The table doesn't exist in database,
  # expect ActiveRecord::ActiveRecordError
  it "should throw ActiveRecordError because the table doesn't exist" do
    foos = Class.new ActiveRecord::Base
    foos.set_table_name "foos"
    foos.acts_as_id_generator :table => "not_exist_sequences_table"

    begin
      foos.create :name => "foo-1"
      raise RuntimeError
    rescue ActiveRecord::ActiveRecordError
      # expected
    end
  end

  # Failure test for ActiveRecord::Base#generate_table_driven_id.
  # The sequence name doesn't exist in the given table,
  # expect TopCoder::Acts::IdGenerator::MissingSequenceError
  it "should throw MissingSequenceError because sequence name doesn't exist in the table" do
    foos = Class.new ActiveRecord::Base
    foos.set_table_name "foos"
    foos.acts_as_id_generator :sequence => "not_exist_sequence_name"

    begin
      foos.create :name => "foo-1"
      raise RuntimeError
    rescue TopCoder::Acts::IdGenerator::MissingSequenceError
      # expected
    end
  end

  # Failure test for ActiveRecord::Base#generate_table_driven_id.
  # The sequence is exhuasted when we generate an id for the saving record,
  # Not fetching db, still using the id blocks fetched last time,
  # expect TopCoder::Acts::IdGenerator::ExhaustedSequenceError
  it "should throw ExhaustedSequenceError when generating an id from an exhausted block" do
    users = Class.new ActiveRecord::Base
    users.set_table_name "users"
    users.acts_as_id_generator :sequence => "foos_seq"

    users_seq = @id_sequences.find :first, :conditions => { :name => "foos_seq" }
    users_seq.next_block_start = 10 ** 12 - 1
    users_seq.save

    # not exhausted yet
    user1 = users.new
    user1.name = "user-1"
    user1.save
    user1.id.should == 10 ** 12 - 1
    #users.create(:name => "foo-1").id.should == 10 ** 12 - 1

    begin
      user2 = users.new
      user2.name = "user-2"
      user2.save
      #foos.create :name => "foo-2"
      raise RuntimeError
    rescue TopCoder::Acts::IdGenerator::ExhaustedSequenceError
      # expected
    end
  end

  # Failure test for ActiveRecord::Base#generate_table_driven_id.
  # The sequence is exhuasted when we generate an id for the saving record,
  # when we fetch new id blocks from db, we found that the sequence is
  # exhausted,
  # expect TopCoder::Acts::IdGenerator::ExhaustedSequenceError
  it "should throw ExhaustedSequenceError when generating an id from an exhausted sequence" do
    users = Class.new ActiveRecord::Base
    users.set_table_name "users"
    users.set_primary_key "nid"
    users.acts_as_id_generator :sequence => "foos_seq"

    users_seq = @id_sequences.find :first, :conditions => { :name => "foos_seq" }
    users_seq.next_block_start = 10 ** 12 - 3
    users_seq.save

    # not exhausted yet
    user1 = users.new
    user1.name = "user-1"
    user1.save
    user1.id.should == 10 ** 12 - 3

    # not exhausted yet
    user2 = users.new
    user2.name = "user-2"
    user2.save
    user2.id.should == 10 ** 12 - 2

    # not exhausted yet
    user3 = users.new
    user3.name = "user-3"
    user3.save
    user3.id.should == 10 ** 12 -1 

    begin
      user4 = users.new
      user4.name = 'user-4'
      user4.save
      raise RuntimeError
    rescue TopCoder::Acts::IdGenerator::ExhaustedSequenceError
      # expected
    end
  end

  # Accuracy test for ActiveRecord::Base#generate_table_driven_id.
  # The table name and sequence name are default values.
  # The ids should be generated correctly.
  # The next block start should be as expected.
  it "should be successful to generate ids with default sequence name and table name" do
    foos = Class.new ActiveRecord::Base
    foos.set_table_name "foos"
    foos.acts_as_id_generator

    # the ids should be 1, 2, 3
    3.times do |t|
      foo = foos.create(:name => 'foo-#{t + 1}').id.should == t + 1
    end

    # the next_block_start should be 4
    id_seq = @id_sequences.find :first, :conditions => { :name => "foos_seq", :exhausted => 0 }
    id_seq.next_block_start.should == 4

    # the ids should be 4, 5
    2.times do |t|
      foo = foos.create(:name => 'foo-#{t + 4}').id.should == t + 4
    end

    # the next_block_start should be 7
    id_seq = @id_sequences.find :first, :conditions => { :name => "foos_seq", :exhausted => 0 }
    id_seq.next_block_start.should == 7
  end

  # Accuracy test for ActiveRecord::Base#generate_table_driven_id.
  # The table name and sequence name are specified.
  # The ids should be generated correctly.
  # The next block start should be as expected.
  it "should be successful to generate ids with specified sequence name and table name" do
    foos = Class.new ActiveRecord::Base
    foos.set_table_name "foos"
    foos.acts_as_id_generator :sequence => "foos_seq_a", :table => "id_sequences_a"

    # the first id is 1
    foo = foos.create(:name => "foo-1").id.should == 1

    # the second id is 2
    foo = foos.create(:name => "foo-2").id.should == 2

    # next_block_start should be 4
    id_seq = @id_sequences_a.find :first, :conditions => { :name => "foos_seq_a", :exhausted => 0 }
    id_seq.next_block_start.should == 4
  end

end

