# Copyright (c) 2008, TopCoder, Inc. All rights reserved.

# This module contains Helper method/class will be mixed-in
# to testing files.
#
# @author TCSDEVELOPER
# @version 1.0
module Helper

  # Migration class for creating/dropping tables used in tests
  class MigrationTestTables < ActiveRecord::Migration
    # creating tables
    def self.up
      create_table :stress do |t|
        t.string :name
        t.datetime :born_on
        t.timestamps
      end
      
      create_table :foos do |t|
        t.string :name
        t.datetime :born_on
        t.timestamps
      end

      create_table :bars do |t|
        t.string :name
        t.datetime :born_on
        t.timestamps
      end
      create_table :users, :id => false  do |t|
        t.column :nid, :decimal, :precision => 13, :scale => 0
        t.column :name, :string
      end
   end

    # dropping tables
    def self.down
      drop_table :foos
      drop_table :bars
      drop_table :users
      drop_table :stress
    end
  end

  # prepares the db connection, init the plugin, etc...
  # Note: by default, it test against mysql,
  # if you wanna test against informix, please use the commented connection
  # and change neccessary configurtion attributes to fit your environment.
  def init
    ActiveRecord::Base.send :include, TopCoder::Acts::IdGenerator
    ActiveRecord::Base.logger = Logger.new STDOUT    
    #ActiveRecord::Base.establish_connection(:adapter => "jdbcmysql",
    #  :database => "rails_test",
    #  :username => "root",
    #  :password => "",
    #  :host => "localhost")
    ActiveRecord::Base.establish_connection(:adapter    => 'informix',
      :username   => 'informix',
      :password   => 'topcoder',
      :servername => 'ol_svr_custom',
      :database   => 'tcs',
      :host       => 'localhost',
      :port       => '9088'
    )
  end
end
