function trim(s) {
	s = s.replace(/^\s+/g, "");   // strip leading
	return s.replace(/\s+$/g, "");// strip trailing
}

function isEmail(s) {
	return !(null == s.match(/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi));
}

function isZipcode(s) {
	return !(null == s.match(/\d{5}(-\d{4})?/));
}

function isPhoneNumber(s) {
	// return !(null == s.match(/^(\+\d)*\s*(\(\d{3}\)\s*)*\d{3}(-{0,1}|\s{0,1})\d{2}(-{0,1}|\s{0,1})\d{2}$/));
	return !(null == s.match(/^\D*\d{3}\D*\d{3}\D*\d{4}/));
}

function isUsername(s) {
	return (null == s.match(/[\s\*]/));
}

function isPassword(s) {
	return (null == s.match(/\s/));
}

function validatePassword(password) {
	if (!password || trim(password).length==0) {
		alert('Please enter password.');
		return false;
	}
	if (password.length < 6 || password.length > 25) {
		alert('The length for password should be 6-25 characters.');
		return false;
	}
	if (!isPassword(password)) {
		alert('Passowrd should not contain whitespace.');
		return false;
	}
	return true;
}

function validatePhone(phone) {
	if (!phone || trim(phone).length==0) {
		alert('Please enter a phone.');	
		return false;
	}
	if (phone.length > 30) {
		alert('The maximum length for phone is 30 characters.');
		return false;
	}
	if (!isPhoneNumber(phone)) {
		alert('Please enter a valid phone. The format should be like:\r\n 2223334444, 222 333 4444, (222) 333 4444, (555) 555-5555');
		return false;
	}
	return true;
}

function validateZipcode(zipcode) {
	if (!zipcode || trim(zipcode).length==0) {
		alert('Please enter a zipcode.');	
		return false;
	}
	if (zipcode.length > 10) {
		alert('The maximum length for zipcode is 10 characters.');
		return false;
	}
	if (!isZipcode(zipcode))
	{
		alert('Please enter a valid zipcode.');
		return false;
	}
	return true;
}

function validateUsername(username) {
	if (!username || trim(username).length == 0) {
		alert('Please enter a user name.');
		return false;
	}
	if (username.length < 6 || username.length > 25) {
		alert('The length for user name is should be 6-25 characters.');
		return false;
	}
	if (!isUsername(username)) {
		alert('User name should not contain whitespaces or wildcards.');
		return false;
	}
	return true;
}