# Copyright (c) 2008, TopCoder, Inc. All rights reserved.

require 'active_record'
require File.dirname(__FILE__) + '/../lib/acts_as_id_generator'
require 'spec_helper'


# This spec contains a demo usage of this plugin.
#
# @author dmks, TCSDEVELOPER
# @version 1.0

# mix-in Helper module
include Helper

# prepares the db connection, init the plugin, etc...
# defined in Helper module
init

# the class will be decorate by acts_as_id_generator
class Foo < ActiveRecord::Base
  acts_as_id_generator :sequence => "foo_seq", :table => "id_sequences"
end

# the class will be decorate by acts_as_id_generator
class Bar < ActiveRecord::Base
  acts_as_id_generator :sequence => "foo_seq", :table => "id_sequences"
end

# demo usage of this component
# Assume we have a table foos,
# Also assume there is one row in id_sequences table:
# (name => "foo_seq", next_block_start => 1, block_size => 10, exhausted => 0)
# We will create 2o foos
# The generated ids for these foos should be from 1 to 20
# The foo_seq will finally be:
# (name => "foo_seq", next_block_start => 21, block_size => 10, exhausted => 0)
describe TopCoder::Acts::IdGenerator do
  # creates tables
  before(:all) do
    MigrationTestTables.up
  end

  # inserts an sequence "foo_seq" into id_sequences table
  before(:each) do
    @id_sequences = Class.new ActiveRecord::Base
    @id_sequences.set_table_name "id_sequences"
    @id_sequences.set_primary_key "name"
    foo_seq = @id_sequences.new
    foo_seq.name = "foo_seq"
    foo_seq.next_block_start = 1
    foo_seq.block_size = 10
    foo_seq.save
  end

  # removes all added rows
  after(:each) do
    Foo.delete_all
    Bar.delete_all
    @id_sequences.delete_all
  end

  # removes the created tables
  after(:all) do
    MigrationTestTables.down
  end

  # demo usage of this plugin.
  # We will create 20 foos
  # The generated ids for these foos should be from 1 to 20
  # The foo_seq will finally be:
  # (name => "foo_seq", next_block_start => 21, block_size => 10, exhausted => 0)
  it "should be successful to generate expected twenty ids" do
    10.times do |i|
      foo = Foo.create :name => "foo-#{i + 1}"
      foo.id.should == i + 1
      bar = Bar.create :name => "barr-#{i + 1}"
      bar.id.should == i + 11
    end
    id_sequence = @id_sequences.find :first, :conditions => { :name => "foo_seq" }
    id_sequence.next_block_start.should == 21
    id_sequence.exhausted.should == 0
  end
end
