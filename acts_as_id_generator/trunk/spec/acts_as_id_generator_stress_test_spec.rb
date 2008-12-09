# Copyright (c) 2008, TopCoder, Inc. All rights reserved.

$LOAD_PATH.unshift(File.expand_path("#{File.dirname(__FILE__)}"))
require File.dirname(__FILE__) + '/../lib/acts_as_id_generator'
require "spec_helper"

include Helper

# prepares the db connection, init the plugin, etc...
# defined in Helper module
init

# Stress Tests for Acts As Id Generator
describe TopCoder::Acts::IdGenerator do
  
  before(:all) do
    MigrationTestTables.up
  end

  # inserts data
  before(:each) do
    @id_sequences_a = Class.new ActiveRecord::Base
    @id_sequences_a.set_table_name "id_sequences_a"
    @id_sequences_a.set_primary_key "name"
    stress_seq_a = @id_sequences_a.new
    stress_seq_a.name = "stress_seq_a"
    stress_seq_a.next_block_start = 1
    stress_seq_a.block_size = 50
    stress_seq_a.save
  end

  # removes all added rows
  after(:each) do
    # removes all rows in "foos" table
    stress = Class.new ActiveRecord::Base
    stress.set_table_name "stress"
    stress.delete_all
    @id_sequences_a.delete_all
  end

  # removes the created tables
  after(:all) do
    MigrationTestTables.down
  end

  # Stress test for acts_as_id_generator. Run it 1000 times.
  it "run acts as id generator 1000 times" do
    stress = Class.new ActiveRecord::Base
    stress.set_table_name "stress"
    stress.acts_as_id_generator :sequence => "stress_seq_a", :table => "id_sequences_a"
    start_time = Time.new
    10.times do |t|
      stress.create(:name => "stress-#{t + 1}")
    end
    printf("generates 1000 ids costs %.6fs\n", Time.new - start_time);
  end
end
