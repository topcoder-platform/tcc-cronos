	function trimStr(s) {
		s = s.replace( /^\s+/g, "" );    // strip leading
		return s.replace( /\s+$/g, "" ); // strip trailing
	}

	function isZipcode(s) {
		return !(null == s.match(/\d{5}(-\d{4})?/));
	}

	function isPhoneNumber(s) {
		return !(null == s.match(/^(\+\d)*\s*(\(\d{3}\)\s*)*\d{3}(-{0,1}|\s{0,1})\d{2}(-{0,1}|\s{0,1})\d{2}$/));
	}

	function containWhitespace(s) {
		return !(null == s.match(/\s/));
	}

	function validateMyUser() {
		passwordText = document.getElementById('password').value;
		if (!passwordText || trimStr(passwordText).length == 0) {
			alert('Please enter password.');
			return false;
		}
		passwordText = trimStr(passwordText);
		document.getElementById('password').value = passwordText;
		if (passwordText.length < 6 || passwordText.length > 64) {
			alert('The length for password should be 6-64 characters.');
			return false;
		}
		if (containWhitespace(passwordText))
		{
			alert('Passowrd should not contain whitespace.');
			return false;
		}

		rePasswordText = document.getElementById('rePassword').value;
		if (!rePasswordText || trimStr(rePasswordText).length == 0) {
			alert('Please enter confirm password.');
			return false;
		}		
		rePasswordText = trimStr(rePasswordText);
		document.getElementById('password').value = rePasswordText;
		if (rePasswordText.length < 6 || rePasswordText.length > 64) {
			alert('The length for confirm password should be 6-64 characters.');
			return false;
		}
		if (containWhitespace(passwordText))
		{
			alert('Confirm passowrd should not contain whitespace.');
			return false;
		}
		if (passwordText!=rePasswordText) {
			alert('The password and confirm password should be same.');
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
			alert('Please enter a email.');
			return false;
		}
		if (!isEmail(emailText)) {
			alert('Please input a valid email');
			return false;
		}
		
		line1Text = document.getElementById('line1').value;
		if (!line1Text || trimStr(line1Text).length == 0) {
			alert('Please enter a user address.');
			return false;
		}
		if (line1Text.length > 100) {
			alert('The maximum length for user addres is 100 characters.');
			return false;
		}
		
		line2Text = document.getElementById('line2').value;
		if (!line2Text && line2Text.length > 100) {
			alert('The maximum length for user addres line2 is 100 characters.');
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
			alert('Please enter a zip code.');
			return false;
		}
		if (zipCodeText.length > 10) {
			alert('The maximum length for zip code is 10 characters.');
			return false;
		}
		if (!isZipcode(zipCodeText))
		{
			alert('Please enter a valid zipcode.');
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
	}

	function validateAdministrator() {
		userNameText = document.getElementById('userName').value;
		if (!userNameText || trimStr(userNameText).length == 0) {
			alert('Please enter a user name.');
			return false;
		}
		userNameText=trimStr(userNameText);
		document.getElementById('userName').value=userNameText;
		if (containWhitespace(userNameText))
		{
			alert('User name should not contain white space.');
			return false;
		}
		if (userNameText.length < 6 || userNameText.length > 64) {
			alert('The length for user name is should be 6-64 characters.');
			return false;
		}
		return validateMyUser();
		function trimStr(s) {
			s = s.replace( /^\s+/g, "" );// strip leading
			return s.replace( /\s+$/g, "" );// strip trailing
		}
	}

	function validateUser() {
		if (!validateAdministrator()) {
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
	}

	function validateAddUser() {
		if (!validateAdministrator()) {
			return false;
		}
		userRoleIndex = document.getElementById('userRole').selectedIndex;
		if (userRoleIndex < 1) {
			alert('Please choose a user role.');
			return false;
		}
		return true;
		function trimStr(s) {
			s = s.replace( /^\s+/g, "" );// strip leading
			return s.replace( /\s+$/g, "" );// strip trailing
		}
	}

	function validateUserProfile() {		
		passwordText = document.getElementById('password').value;
		if (passwordText && (passwordText.length > 0) && (trimStr(passwordText).length == 0)) {
			alert('Please enter valid password if need to change, otherwise keep empty.');
			return false;
		}
		if (passwordText && (passwordText.length < 6 || passwordText.length > 64)) {
			alert('The length for password is 6-64 characters.');
			return false;
		}
		if (passwordText && containWhitespace(passwordText))
		{
			alert('Passowrd should not contain white space.');
			return false;
		}
		rePasswordText = document.getElementById('rePassword').value;
		if (rePasswordText && (rePasswordText.length > 0) && (trimStr(rePasswordText).length == 0)) {
			alert('Please enter confirm password if need to change, otherwise keep empty.');
			return false;
		}
		if (rePasswordText && (rePasswordText.length  < 6 || rePasswordText.length > 64)) {
			alert('The length for confirm password is 6-64 characters.');
			return false;
		}		
		if (rePasswordText && containWhitespace(rePasswordText))
		{
			alert('Confirm passowrd should not contain white space.');
			return false;
		}
		if (passwordText!=rePasswordText) {
			alert('The password and confirm password should be same.');
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
			alert('Please enter a email.');
			return false;
		}
		if (!isEmail(emailText)) {
			alert('Please input a valid email');
			return false;
		}
		
		line1Text = document.getElementById('line1').value;
		if (!line1Text || trimStr(line1Text).length == 0) {
			alert('Please enter a user address.');
			return false;
		}
		if (line1Text.length > 100) {
			alert('The maximum length for user addres is 100 characters.');
			return false;
		}
		
		line2Text = document.getElementById('line1').value;
		if (!line2Text && line2Text.length > 100) {
			alert('The maximum length for user addres line2 is 100 characters.');
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
			alert('Please enter a zip code.');
			return false;
		}
		if (zipCodeText.length > 10) {
			alert('The maximum length for zip code is 10 characters.');
			return false;
		}
		if (!isZipcode(zipCodeText))
		{
			alert('Please enter a valid zipcode.');
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
	}

	function validateEditUser() {
		if (!validateUserProfile())
		{
			return false;
		}
		userRoleIndex = document.getElementById('userRole').selectedIndex;
		if (userRoleIndex < 1) {
			alert('Please choose a user role.');
			return false;
		}
		return true;
	}