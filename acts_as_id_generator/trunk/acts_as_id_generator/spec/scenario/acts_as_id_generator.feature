Feature: This module provide a Rails plugin that will allow ActiveRecord models to
easily generate their primary key ids from legacy Id Generator sequences.

  Scenario: decorating an ActiveRecord with acts_as_id_generator
    Given the table name is foos and sequence name is foos_seq
    When I decorate an ActiveRecord with acts_as_id_generator
    Then It should not raise any error

  Scenario: successfully generating an id while saving a new object
    Given the next block start is 1
    When I save 2 new record(s)
    Then the 1 saved record id should be 1
    And the 2 saved record id should be 2
    And the next block start in id_sequences table should be 4

  Scenario: failed to generate an id while saving a new object because sequence name doesn't exist
    Given the table name is foos and sequence name is not_exist_sequence_name
    And the next block start is 1
    When I save 1 new record(s)
    Then TopCoder::Acts::IdGenerator::MissingSequenceError should be thrown

  Scenario: failed to generate an id while saving a new object because table name doesn't exist
    Given the table name is not_exist_table and sequence name is foos_seq
    And the next block start is 1
    When I save 1 new record(s)
    Then ActiveRecord::StatementInvalid should be thrown
