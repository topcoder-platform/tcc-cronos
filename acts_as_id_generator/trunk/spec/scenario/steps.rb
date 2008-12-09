# Copyright (c) 2008, TopCoder, Inc. All rights reserved.

require 'spec'
require File.dirname(__FILE__) + '/../../lib/acts_as_id_generator'
require File.dirname(__FILE__) + '/../spec_helper'

# This file contains running steppes for defined scenario.
#
# @author TCSDEVELOPER
# @version 1.0

# mix-in Helper module
include Helper

# prepares the db connection, init the plugin, etc...
# defined in Helper module
init

# runs before every scenario
# Sets up db
Before do
  MigrationTestTables.up

  @id_sequences = Class.new ActiveRecord::Base
  @id_sequences.set_table_name "id_sequences"
  @id_sequences.set_primary_key "name"
  id_sequence = @id_sequences.new
  id_sequence.name = "foos_seq"
  id_sequence.next_block_start = 1
  id_sequence.block_size = 3
  id_sequence.save
end

# runs after every scenario
# cleans up db
After do
  @foos.delete_all unless @foos.nil?
  @id_sequences.delete_all
  MigrationTestTables.down
end

# updatse the sequence with next_block_start set to the given value
Given /^the next block start is (\d+)$/ do |next_block_start|
  id_sequence = @id_sequences.find(:first, :conditions => "name = 'foos_seq'")
  id_sequence.next_block_start = next_block_start.to_i
  id_sequence.exhausted = 1 if next_block_start.to_i >= 10 ** 12
  id_sequence.save
end

# gets table_name and sequence_name specified by scenario
Given /^the table name is (\w+) and sequence name is (\w+)$/ do |table_name, sequence_name|
  @table_name = table_name
  @sequence_name = sequence_name
end

# creates the given number record(s)
# if any error occurs, the error class is logged
When /^I save (\d+) new record\(s\)$/ do |count|
  @foos = Class.new ActiveRecord::Base
  @foos.set_table_name "foos"
  @foos.acts_as_id_generator :table => @table_name, :sequence => @sequence_name
  # runs count times
  1.upto(count.to_i) do |x|
    begin
      foo = @foos.create :name => "foo-#{x}"
    rescue StandardError => ex
      @error_class = ex.class.name
    end
  end
end

# decoreates the @foos with acts_as_id_generator
When /^I decorate an ActiveRecord with acts_as_id_generator$/ do
  @foos = Class.new ActiveRecord::Base
  @foos.set_table_name "foos"
  @foos.acts_as_id_generator :table => @table_name, :sequence => @sequence_name
end

# the next_block_start value of the given sequence should be as expected
Then /^the next block start in id_sequences table should be (\d+)$/ do |result_start|
  @id_sequences.find(:first, :conditions => "name = 'foos_seq'").next_block_start.should == result_start.to_i
end

# the record id should be as expected
Then /^the (\d+) saved record id should be (\d+)$/ do |x, result_id|
  foo = @foos.find :first, :conditions => { :name => "foo-#{x}" }
  foo.id.should == result_id
end

# the error class name should be expected
Then /^([A-Za-z0-9_:]+) should be thrown$/ do |error_class|
  @error_class.should == error_class
end

# no error raised
Then /^It should not raise any error$/ do
  @error_class.should == nil
end
