    function validateCompany() {
		companyNameText = document.getElementById('companyName').value;
		if (!companyNameText || trimStr(companyNameText).length == 0) {
			alert('Please enter a company name.');
			return false;
		}
		if (companyNameText.length > 64) {
			alert('The maximum length for company name is 64 characters.');
			return false;
		}
		
		firstNameText = document.getElementById('firstName').value;
		if (!firstNameText || trimStr(firstNameText).length == 0) {
			alert('Please enter a first name.');
			return false;
		}
		if (firstNameText.length > 64) {
			alert('The maximum length for first name is 64 characters.');
			return false;
		}
		
		lastNameText = document.getElementById('lastName').value;
		if (!lastNameText || trimStr(lastNameText).length == 0) {
			alert('Please enter a last name.');
			return false;
		}
		if (lastNameText.length > 64) {
			alert('The maximum length for last name is 64 characters.');
			return false;
		}
		
		phoneText = document.getElementById('phone').value;
		if (!phoneText || trimStr(phoneText).length == 0) {
			alert('Please enter a phone.');
			return false;
		}
		if (phoneText.length > 30) {
			alert('The maximum length for phone is 30 characters.');
			return false;
		}
		if (!isPhoneNumber(phoneText))
		{
			alert('Please enter a valid phone.');
			return false;
		}
		
		emailText = document.getElementById('email').value;
		if (!emailText || trimStr(emailText).length == 0) {
			alert('Please enter an email.');
			return false;
		}
		if (!isEmail(emailText)) {
			alert('Please input a valid email');
			return false;
		}
		
		line1Text = document.getElementById('companyLine1').value;
		if (!line1Text || trimStr(line1Text).length == 0) {
			alert('Please enter a company address.');
			return false;
		}
		if (line1Text.length > 100) {
			alert('The maximum length for company address is 100 characters.');
			return false;
		}
		
		line2Text = document.getElementById('companyLine2').value;
		if (!line1Text || line2Text.length > 100) {
			alert('The maximum length for company address line 2 is 100 characters.');
			return false;
		}
		
		cityText = document.getElementById('city').value;
		if (!cityText || trimStr(cityText).length == 0) {
			alert('Please enter a city.');
			return false;
		}
		if (cityText.length > 30) {
			alert('The maximum length for city is 30 characters.');
			return false;
		}
		
		stateIndex = document.getElementById('state').selectedIndex ;
		if (stateIndex < 1) {
			alert('Please choose a state.');
			return false;
		}
		
		zipCodeText = document.getElementById('zipCode').value ;
		if (!zipCodeText || trimStr(zipCodeText).length == 0) {
			alert('Please enter a zipcode.');
			return false;
		}
		if (zipCodeText.length > 10) {
			alert('The maximum length for zip code is 10 characters.');
			return false;
		}
		if (!isZipcode(zipCodeText)) {
			alert('Please enter a valid zipcode.');
			return false;
		}
		
		passcodeText = document.getElementById('passcode').value ;
		if (!passcodeText || trimStr(passcodeText).length == 0) {
			alert('Please enter a passcode.');
			return false;
		}
		if (passcodeText.length > 64) {
			alert('The maximum length for passcode is 64 characters.');
			return false;
		}

		return true;
		
		function trimStr(s) {
			s = s.replace( /^\s+/g, "" );// strip leading
			return s.replace( /\s+$/g, "" );// strip trailing
		}

		function isEmail(s) {
			return !(null == s.match(/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi));
		}
		
		function isZipcode(s) {
			return !(null == s.match(/\d{5}(-\d{4})?/));
		}
		
		function isPhoneNumber(s) {
			//return !(null == s.match(/^(\+\d)*\s*(\(\d{3}\)\s*)*\d{3}(-{0,1}|\s{0,1})\d{2}(-{0,1}|\s{0,1})\d{2}$/));
			return !(null == s.match(/^\D*\d{3}\D*\d{3}\D*\d{4}/));
		}
	}