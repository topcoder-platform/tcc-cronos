class CreateCowsTable < ActiveRecord::Migration
  def self.up
    create_table :cows do |t|
      t.string :name
      t.datetime :born_on

      t.timestamps
    end
  end

  def self.down
    drop_table :cows
  end
end