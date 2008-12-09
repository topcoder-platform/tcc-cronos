require 'acts_as_id_generator'
ActiveRecord::Base.send(:include, TopCoder::Acts::IdGenerator)

