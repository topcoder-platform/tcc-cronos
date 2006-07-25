
package com.topcoder.management.scorecard.validation;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.validation.ValidationException;

/**
 * This is the default implementation of the ScorecardValidator interface to provide scorecard validation functions.
 * It validates the scorecard base on the following rules:
 * Data Validation component should be used to check for these rules.
 * 1/ For scorecard:
 * - scorecard_status_id: Must be greater than zero
 * - scorecard_type_id: Must be greater than zero
 * - project_category_id: Must be greater than zero
 * - name: Must not empty. Length must smaller than or equal to 64
 * - version: See Component Specification, Algorithm section.
 * - min_score: Must be greater than or equal to zero
 * - max_score: Must be greater than min_score.
 * 2/ For group:
 * - name: Length must smaller than or equal to 64
 * - weight: Must be greater than zero and smaller than 100
 * - Total weight must equals to 100
 * 3/ For section:
 * - name: Length must smaller than  or equal to 64
 * - weight: Must be greater than zero and smaller than 100
 * - Total weight must equals to 100
 * 4/ For question:
 * - description: Must not be empty
 * - guideline: Can be null or empty
 * - weight: Must be greater than zero and smaller than 100
 * - Total weight must equals to 100
 * Thread safety: This class is immutable and thread safe.
 * 
 */
public class DefaultScorecardValidator implements com.topcoder.management.scorecard.ScorecardValidator {

/**
 * Create a new instance of DefaultScorecardValidator. This class does not have
 * any configuration settings. But the namespace parameter is provided to comply with the
 * contract defined in ScorecardValidator interface.
 * Implementation notes:
 * - This is a blank constructor.
 * 
 * 
 * @param namespace The namespace to load configuration settings.
 */
    public  DefaultScorecardValidator(String namespace) {        
        // your code here
    } 

/**
 * Validate the given scorecard and its sub items base on some specific rules. This method will
 * throw ValidationException on the first item that is not follow the a rule.For the set of rules, see
 * class documentation of this class.
 * Implementation notes:
 * - Walk through the scorecard tree
 * - Use the rules defined in class documentation to check items
 * - Throw ValidationException with meaningful message for each problem.
 * 
 * 
 * @param scorecard The scorecard to validate.
 * @throws ValidationException if validation fails.
 */
    public void validateScorecard(Scorecard scorecard) throws ValidationException {        
        // your code here
    } 
 }
