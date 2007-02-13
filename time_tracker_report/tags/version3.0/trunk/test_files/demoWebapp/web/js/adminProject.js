	function addContractorID(id) {
		var new_array = new Array(contractorIDs.length + 1);
		for (var i = 0; i < contractorIDs.length; i++) {
			new_array[i] = contractorIDs[i];
		}
		new_array[new_array.length - 1] = id;
		contractorIDs = new_array;
	}

	function removeContractorID(id) {
		var numFound = 0;
		for (var i = 0; i < contractorIDs.length; i++) {
			if (contractorIDs[i] == id) {
				numFound++;
			}
		}
		var new_array = new Array(contractorIDs.length - numFound);
		var j = 0;
		for (var i = 0; i < contractorIDs.length; i++) {
			if (contractorIDs[i] != id) {
				new_array[j++] = contractorIDs[i];
			}
		}
		contractorIDs = new_array;
	}

	function deleteEmployeeAndVerify(idEmployee, hasTimes, hasExpense) {
		if (hasExpense || hasTimes){
			alert("This worker cannot be removed, because he or she\nalready has time or expense entries for this project.");
		}else {
			deleteEmployee(idEmployee);
		}
	}

	function deleteEmployee(idEmployee) {
		var employeeName = 'with id ' + idEmployee;
		var cmbEmp = document.getElementById('comboEmployee');
		
		for (i = 0; i < cmbEmp.options.length; i++) {
			if (cmbEmp.options[i].value==idEmployee) {
				employeeName = cmbEmp.options[i].text;
			} 		
		}
		
		if (!confirm('Are you sure you want to delete the following employee: ' + employeeName + ' ?')) {
			return;
		}

		if (employeeNo > 0) {
			var table = document.getElementById('results_table');
			var rowForDelete = document.getElementById('employeeRow'+idEmployee);

			table.deleteRow(rowForDelete.rowIndex);
			employeeNo--;
		}
	}
   
	function addEmployee() {
		if (!validateNewEmployee()) {
			return;
		}
		
		var table = document.getElementById('results_table');
		employeeNo++;		

		var comboEmployee = document.getElementById('comboEmployee');
		var newEmployee = comboEmployee.options[comboEmployee.selectedIndex].text;
		var newEmployeeId = comboEmployee.value;
		var newEmployeeStartDate = document.getElementById('employeeStartDate').value;
		var newEmployeeEndDate = document.getElementById('employeeEndDate').value;
		var lastRow = employeeNo + 2;

		comboEmployee.selectedIndex = 0;
		document.getElementById('employeeStartDate').value = "";
		document.getElementById('employeeEndDate').value = "";
		
  		newRow = table.insertRow(lastRow);
		newRow.id = 'employeeRow' + newEmployeeId;

		newCell = newRow.insertCell(0);
		newCell.colSpan=2;

		hidden = document.createElement('input');
		hidden.type='hidden';
		hidden.className='text';
		hidden.value = newEmployeeId;
		hidden.name = 'newEmployeeId-' + newEmployeeId;
  		newCell.appendChild(hidden);
		
  		textNode = document.createTextNode(newEmployee);
  		newCell.appendChild(textNode);


  		newCell = newRow.insertCell(1);
		hidden = document.createElement('input');
		hidden.type='hidden';
		hidden.className='text';
		hidden.value = newEmployeeStartDate;
		hidden.name = 'employeeStartDate-' + newEmployeeId;
  		newCell.appendChild(hidden);

  		textNode = document.createTextNode(newEmployeeStartDate);
  		newCell.appendChild(textNode);


 		newCell = newRow.insertCell(2);
		hidden = document.createElement('input');
		hidden.type='hidden';
		hidden.className='text';
		hidden.value = newEmployeeEndDate;
		hidden.name = 'employeeEndDate-' + newEmployeeId;
  		newCell.appendChild(hidden);

  		textNode = document.createTextNode(newEmployeeEndDate);
  		newCell.appendChild(textNode);


  		var newCell = newRow.insertCell(3);
		newCell.noWrap = true;
		var removeLink = document.createElement('a');
  		removeLink.appendChild(document.createTextNode('Remove'));
  		removeLink.href='javascript:deleteEmployee(' + newEmployeeId + ')';
 		
 		newCell.appendChild(document.createTextNode('[ '));
  		newCell.appendChild(removeLink);
  		newCell.appendChild(document.createTextNode(' ]'));
	}
	
	function deleteContractorAndVerify(idContractor, hasTimes, hasExpense) {
		if (hasExpense || hasTimes){
			alert("This worker cannot be removed, because he or she\nalready has time or expense entries for this project.");
		}else {
			deleteContractor(idContractor);
		}
	}
	
	function deleteContractor(idContractor) {
		var contractorName = 'with id ' + idContractor;
		var cmbContractor = document.getElementById('comboContractor');
		
		for (i = 0; i < cmbContractor.options.length; i++) {
			if (cmbContractor.options[i].value==idContractor) {
				contractorName = cmbContractor.options[i].text;
			} 		
		}
		
		if (!confirm('Are you sure you want to delete the following contractor: ' + contractorName + ' ?')) {
			return;
		}
	
		if (contractorNo > 0) {
			var table = document.getElementById('results_table');
			var rowForDelete = document.getElementById('contractorRow'+idContractor);

			table.deleteRow(rowForDelete.rowIndex);
			contractorNo--;
			removeContractorID(''+idContractor);
		}
	}
	
	function addContractor() {
		if (!validateNewContractor()) {
			return;
		}
		var table = document.getElementById('results_table');
		contractorNo++;		

		var comboContractor = document.getElementById('comboContractor');
		var newContractor = comboContractor.options[comboContractor.selectedIndex].text;
		var newContractorId = comboContractor.value;
		var newContractorRate = document.getElementById('contractorRate').value;
		var newContractorStartDate = document.getElementById('contractorStartDate').value;
		var newContractorEndDate = document.getElementById('contractorEndDate').value;
		var lastRow = table.rows.length;
		
		comboContractor.selectedIndex = 0;
		document.getElementById('contractorStartDate').value = "";
		document.getElementById('contractorEndDate').value = "";
		document.getElementById('contractorRate').value = "";
		
  		newRow = table.insertRow(lastRow);
		newRow.id = 'contractorRow' + newContractorId;


		newCell = newRow.insertCell(0);

		hidden = document.createElement('input');
		hidden.type='hidden';
		hidden.value=newContractorId;
		hidden.name='newContractorId-' + newContractorId;
  		newCell.appendChild(hidden);
		
		textNode = document.createTextNode(newContractor);
  		newCell.appendChild(textNode);


		newCell = newRow.insertCell(1);
		newCell.align='center';
		hidden = document.createElement('input');
		hidden.type='text';
		hidden.value=newContractorRate;
		hidden.name='contractorRate-' + newContractorId;
		hidden.id = hidden.name;
		hidden.className='inputBox';
		hidden.size=10;
  		newCell.appendChild(hidden);

		newCell = newRow.insertCell(2);
		hidden = document.createElement('input');
		hidden.type='hidden';
		hidden.className='text';
		hidden.value = newContractorStartDate;
		hidden.name = 'contractorStartDate-' + newContractorId;
  		newCell.appendChild(hidden);
		
  		textNode = document.createTextNode(newContractorStartDate);
  		newCell.appendChild(textNode);

 		newCell = newRow.insertCell(3);
		hidden = document.createElement('input');
		hidden.type='hidden';
		hidden.className='text';
		hidden.value = newContractorEndDate;
		hidden.name = 'contractorEndDate-' + newContractorId;
  		newCell.appendChild(hidden);
	

  		textNode = document.createTextNode(newContractorEndDate);
  		newCell.appendChild(textNode);

  		newCell = newRow.insertCell(4);
		newCell.noWrap = true;
		removeLink = document.createElement('a');
  		removeLink.appendChild(document.createTextNode('Remove'));
  		removeLink.href='javascript:deleteContractor(' + newContractorId + ')';
		 		
 		newCell.appendChild(document.createTextNode('[ '));
  		newCell.appendChild(removeLink);
  		newCell.appendChild(document.createTextNode(' ]'));

		addContractorID(''+newContractorId);
	}
	
	function validateProject() {
	
		projectNameText = document.getElementById('projectName').value;
		if (!projectNameText || trimStr(projectNameText).length == 0) {
			alert('Please enter a project name.');
			return false;
		}

		if (projectNameText.length > 35) {
			alert('The maximum length for project name is 35 characters.');
			return false;
		}
		
		projectDescText = document.getElementById('projectDescription').value;
		if (!projectDescText || trimStr(projectDescText).length == 0) {
			alert('Please enter a project description.');
			return false;
		}
		
		if (projectDescText.length > 255) {
			alert('The maximum length for the project description is 255 characters.');
			return false;
		}
		
		projectClient = document.getElementById('projectClient');
		if (projectClient.selectedIndex == 0) {
			alert('Please select a project client.');
			return false;
		}
		
		try {
		projectManager = document.getElementById('projectManager');
			if (projectManager.selectedIndex == 0) {
				alert('Please select a project manager.');
				return false;
			}
		} catch(e) {
			alert(e);
		}


		projectStartDateText = document.getElementById('projectStartDate').value;
		if (!validateDate(projectStartDateText)) {
			alert('Please select a valid start date for the project.');
			return false;
		}
		
		projectEndDateText = document.getElementById('projectEndDate').value;
		if (!validateDate(projectEndDateText)) {
			alert('Please select a valid end date for the project.');
			return false;
		}
		
		var dateParts = projectStartDateText.split("-");
		var month = parseInt(dateParts[0],10);
		var day = parseInt(dateParts[1],10);
		var year = parseInt(dateParts[2],10);
			
		var startDate = new Date(year, month-1, day);
		
		var dateParts = projectEndDateText.split("-");
		var month = parseInt(dateParts[0],10);
		var day = parseInt(dateParts[1],10);
		var year = parseInt(dateParts[2],10);
		
		var endDate = new Date(year, month-1, day);
		
		if (startDate.getTime() > endDate.getTime()) {
			alert('The project end date cannot be before the start date.');
			return false;
		}

		for (var i = 0; i < contractorIDs.length; i++) {
			if (!validatePayRate('contractorRate-' + contractorIDs[i])) {
				return false;
			}
		}
		
		return true;

		function trimStr(s) {
			s = s.replace( /^\s+/g, "" );// strip leading
			return s.replace( /\s+$/g, "" );// strip trailing
		}
	}
	
	function validateNewEmployee() {
		var comboEmployee = document.getElementById('comboEmployee');
		var newEmployee = comboEmployee.options[comboEmployee.selectedIndex].text;
		var newEmployeeId = comboEmployee.value;
		
		if (comboEmployee.selectedIndex == 0) {
			alert('Please select an employee to add.');
			return false;
		}
		
		var newEmployeeId = comboEmployee.value;
		var existingEmployeRow = document.getElementById('employeeRow'+newEmployeeId);
		if (existingEmployeRow) {
			alert('The selected employee has already been added to the project.');
			return false;
		}
		
		employeeStartDateText = document.getElementById('employeeStartDate').value;
		if (!validateDate(employeeStartDateText)) {
			alert('Please select a valid start date for the new employee.');
			return false;
		}
		
		employeeEndDateText = document.getElementById('employeeEndDate').value;
		if (!validateDate(employeeEndDateText)) {
			alert('Please select a valid end date for the new employee.');
			return false;
		}
		
		var dateParts = employeeStartDateText.split("-");
		var month = parseInt(dateParts[0],10);
		var day = parseInt(dateParts[1],10);
		var year = parseInt(dateParts[2],10);
			
		var startDate = new Date(year, month-1, day);
		
		var dateParts = employeeEndDateText.split("-");
		var month = parseInt(dateParts[0],10);
		var day = parseInt(dateParts[1],10);
		var year = parseInt(dateParts[2],10);
		
		var endDate = new Date(year, month-1, day);
		
		if (startDate.getTime() > endDate.getTime()) {
			alert('The employee end date cannot be before the start date.');
			return false;
		}
		
		return true;
	}

	function validatePayRate(id) {
		var contractorRateText = document.getElementById(id).value;
		if (contractorRateText.length == 0 || contractorRateText.match('^[0-9\.]*$') == null) {
			alert('Please enter a valid dollar amount for pay rate.');
			return false;
		}
		return true;
	}

	function validateNewContractor() {

		var newContractorRate = document.getElementById('contractorRate').value;
			
		var comboContractor = document.getElementById('comboContractor');
		var newContractor = comboContractor.options[comboContractor.selectedIndex].text;
		var newContractorId = comboContractor.value;
		
		if (comboContractor.selectedIndex == 0) {
			alert('Please select a contractor to add.');
			return false;
		}
		
		if (!validatePayRate('contractorRate')) {
			return false;
		}
		
		if (comboContractor.selectedIndex == 0) {
			alert('Please select a contractor to add.');
			return false;
		}			
		
		var existingContractorRow = document.getElementById('contractorRow'+newContractorId);
		if (existingContractorRow) {
			alert('The selected contractor has already been assigned to the project.');
			return false;
		}
		
		contractorStartDateText = document.getElementById('contractorStartDate').value;
		if (!validateDate(contractorStartDateText)) {
			alert('Please enter a valid start date for the new contractor.');
			return false;
		}
		
		contractorEndDateText = document.getElementById('contractorEndDate').value;
		if (!validateDate(contractorEndDateText)) {
			alert('Please enter a valid end date for the new contractor.');
			return false;
		}
		
		var dateParts = contractorStartDateText.split("-");
		var month = parseInt(dateParts[0],10);
		var day = parseInt(dateParts[1],10);
		var year = parseInt(dateParts[2],10);
			
		var startDate = new Date(year, month-1, day);
		
		var dateParts = contractorEndDateText.split("-");
		var month = parseInt(dateParts[0],10);
		var day = parseInt(dateParts[1],10);
		var year = parseInt(dateParts[2],10);
		
		var endDate = new Date(year, month-1, day);
		
		if (startDate.getTime() > endDate.getTime()) {
			alert('The contractor end date cannot be before the start date.');
			return false;
		}
		
		return true;
	}
	
	function validateDate(inp) {
		try {
			var dateParts = inp.split("-");
			inpMonth = parseInt(dateParts[0],10);
			inpDay = parseInt(dateParts[1],10);
			inpYear = parseInt(dateParts[2],10);
			
			var testDate = new Date(inpYear, inpMonth-1, inpDay);
			var testMonth = testDate.getMonth() + 1;
			var testDay = testDate.getDate();
			var testYear = testDate.getFullYear();
						
			if (isNaN(testMonth) || isNaN(testDay) || isNaN(testYear)) {
			
				return false;
			}
			
			if (isNaN(inpMonth) || isNaN(inpDay) || isNaN(inpYear)) {
				return false;
			}
				
			if (testMonth != inpMonth || testDay != inpDay || testYear != inpYear) {
				return false;
			}
		} catch(e) {
			return false;
		}
		return true;
	}