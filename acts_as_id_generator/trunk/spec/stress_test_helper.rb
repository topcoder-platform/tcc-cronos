# Copyright (c) 2008, TopCoder, Inc. All rights reserved.

require "java"
require "ifxjdbc.jar"
include_class "com.informix.jdbc.IfxDriver"

# This module contains StressHelper method/class will be mixed-in
# to testing files.
#
# @author iRabbit
# @version 1.0
module StressHelper

  # Migration class for creating/dropping tables used in tests
  class StressTestsTables < ActiveRecord::Migration
    # creating tables
    def self.up
      create_table :stress do |t|
        t.string :name
        t.datetime :born_on
        t.timestamps
      end
    end

    # dropping tables
    def self.down
      drop_table :stress
    end
  end

  # prepares the db connection, init the plugin, etc...
  def init
    ActiveRecord::Base.send :include, TopCoder::Acts::IdGenerator
    ActiveRecord::Base.logger = Logger.new STDOUT    
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
