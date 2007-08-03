<%@ page import="com.topcoder.user.profile.UserProfile,
				 com.orpheus.user.persistence.UserConstants" %>

<%  String country = (String)profile.getProperty(UserConstants.ADDRESS_COUNTRY); %>
<c:set var="country" value="<%=country%>" />

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty requestScope['country.error']}">
  <span class="fBold cRed">${requestScope['country.error']}</span><br/>
</c:if>
<select name="country" id="country" class="dropDown" style="width:150px;">
<option value=""></option>
<option value='United States' ${country eq 'United States' ? 'selected="selected"' : ''}>United States</option>
<option value='Afghanistan'  ${country eq 'Afghanistan' ? 'selected="selected"' : ''}>Afghanistan</option>
<option value='Albania'  ${country eq 'Albania' ? 'selected="selected"' : ''}>Albania</option>
<option value='Algeria'  ${country eq 'Algeria' ? 'selected="selected"' : ''}>Algeria</option>
<option value='American Samoa'  ${country eq 'American Samoa' ? 'selected="selected"' : ''}>American Samoa</option>
<option value='Andorra'  ${country eq 'Andorra' ? 'selected="selected"' : ''}>Andorra</option>
<option value='Angola'  ${country eq 'Angola' ? 'selected="selected"' : ''}>Angola</option>
<option value='Anguilla'  ${country eq 'Anguilla' ? 'selected="selected"' : ''}>Anguilla</option>
<option value='Antarctica'  ${country eq 'Antarctica' ? 'selected="selected"' : ''}>Antarctica</option>
<option value='Antigua'  ${country eq 'Antigua' ? 'selected="selected"' : ''}>Antigua</option>
<option value='Argentina'  ${country eq 'Argentina' ? 'selected="selected"' : ''}>Argentina</option>
<option value='Armenia'  ${country eq 'Armenia' ? 'selected="selected"' : ''}>Armenia</option>
<option value='Aruba'  ${country eq 'Aruba' ? 'selected="selected"' : ''}>Aruba</option>
<option value='Australia'  ${country eq 'Australia' ? 'selected="selected"' : ''}>Australia</option>
<option value='Austria'  ${country eq 'Austria' ? 'selected="selected"' : ''}>Austria</option>
<option value='Azerbaijan'  ${country eq 'Azerbaijan' ? 'selected="selected"' : ''}>Azerbaijan</option>
<option value='Bahamas'  ${country eq 'Bahamas' ? 'selected="selected"' : ''}>Bahamas</option>
<option value='Bahrain'  ${country eq 'Bahrain' ? 'selected="selected"' : ''}>Bahrain</option>
<option value='Bangladesh'  ${country eq 'Bangladesh' ? 'selected="selected"' : ''}>Bangladesh</option>
<option value='Barbados'  ${country eq 'Barbados' ? 'selected="selected"' : ''}>Barbados</option>
<option value='Belarus'  ${country eq 'Belarus' ? 'selected="selected"' : ''}>Belarus</option>
<option value='Belgium'  ${country eq 'Belgium' ? 'selected="selected"' : ''}>Belgium</option>
<option value='Belize'  ${country eq 'Belize' ? 'selected="selected"' : ''}>Belize</option>
<option value='Benin'  ${country eq 'Benin' ? 'selected="selected"' : ''}>Benin</option>
<option value='Bermuda'  ${country eq 'Bermuda' ? 'selected="selected"' : ''}>Bermuda</option>
<option value='Bhutan'  ${country eq 'Bhutan' ? 'selected="selected"' : ''}>Bhutan</option>
<option value='Bolivia'  ${country eq 'Bolivia' ? 'selected="selected"' : ''}>Bolivia</option>
<option value='Bosnia and Herzegovina'  ${country eq 'Bosnia and Herzegovina' ? 'selected="selected"' : ''}>Bosnia and Herzegovina</option>
<option value='Botswana'  ${country eq 'Botswana' ? 'selected="selected"' : ''}>Botswana</option>
<option value='Brazil'  ${country eq 'Brazil' ? 'selected="selected"' : ''}>Brazil</option>
<option value='British Virgin Islands'  ${country eq 'British Virgin Islands' ? 'selected="selected"' : ''}>British Virgin Islands</option>
<option value='Brunei'  ${country eq 'Brunei' ? 'selected="selected"' : ''}>Brunei</option>
<option value='Bulgaria'  ${country eq 'Bulgaria' ? 'selected="selected"' : ''}>Bulgaria</option>
<option value='Burkina Faso'  ${country eq 'Burkina Faso' ? 'selected="selected"' : ''}>Burkina Faso</option>
<option value='Burma'  ${country eq 'Burma' ? 'selected="selected"' : ''}>Burma</option>
<option value='Burundi'  ${country eq 'Burundi' ? 'selected="selected"' : ''}>Burundi</option>
<option value='Cambodia'  ${country eq 'Cambodia' ? 'selected="selected"' : ''}>Cambodia</option>
<option value='Cameroon'  ${country eq 'Cameroon' ? 'selected="selected"' : ''}>Cameroon</option>
<option value='Canada'  ${country eq 'Canada' ? 'selected="selected"' : ''}>Canada</option>
<option value='Cape Verde'  ${country eq 'Cape Verde' ? 'selected="selected"' : ''}>Cape Verde</option>
<option value='Central African Republic'  ${country eq 'Central African Republic' ? 'selected="selected"' : ''}>Central African Republic</option>
<option value='Chad'  ${country eq 'Chad' ? 'selected="selected"' : ''}>Chad</option>
<option value='Chile'  ${country eq 'Chile' ? 'selected="selected"' : ''}>Chile</option>
<option value='China'  ${country eq 'China' ? 'selected="selected"' : ''}>China</option>
<option value='Colombia'  ${country eq 'Colombia' ? 'selected="selected"' : ''}>Colombia</option>
<option value='Comoros'  ${country eq 'Comoros' ? 'selected="selected"' : ''}>Comoros</option>
<option value='Congo'  ${country eq 'Congo' ? 'selected="selected"' : ''}>Congo</option>
<option value='Cook Islands'  ${country eq 'Cook Islands' ? 'selected="selected"' : ''}>Cook Islands</option>
<option value='Costa Rica'  ${country eq 'Costa Rica' ? 'selected="selected"' : ''}>Costa Rica</option>
<option value='Cote dIvoire'  ${country eq "Cote d'Ivoire" ? 'selected="selected"' : ''}>Cote d'Ivoire</option>
<option value='Croatia'  ${country eq 'Croatia' ? 'selected="selected"' : ''}>Croatia</option>
<option value='Cuba'  ${country eq 'Cuba' ? 'selected="selected"' : ''}>Cuba</option>
<option value='Cyprus'  ${country eq 'Cyprus' ? 'selected="selected"' : ''}>Cyprus</option>
<option value='Czech Republic'  ${country eq 'Czech Republic' ? 'selected="selected"' : ''}>Czech Republic</option>
<option value='Denmark'  ${country eq 'Denmark' ? 'selected="selected"' : ''}>Denmark</option>
<option value='Djibouti'  ${country eq 'Djibouti' ? 'selected="selected"' : ''}>Djibouti</option>
<option value='Dominica'  ${country eq 'Dominica' ? 'selected="selected"' : ''}>Dominica</option>
<option value='Dominican Republic'  ${country eq 'Dominican Republic' ? 'selected="selected"' : ''}>Dominican Republic</option>
<option value='Ecuador'  ${country eq 'Ecuador' ? 'selected="selected"' : ''}>Ecuador</option>
<option value='Egypt'  ${country eq 'Egypt' ? 'selected="selected"' : ''}>Egypt</option>
<option value='El Salvador'  ${country eq 'El Salvador' ? 'selected="selected"' : ''}>El Salvador</option>
<option value='Equatorial Guinea'  ${country eq 'Equatorial Guinea' ? 'selected="selected"' : ''}>Equatorial Guinea</option>
<option value='Eritrea'  ${country eq 'Eritrea' ? 'selected="selected"' : ''}>Eritrea</option>
<option value='Estonia'  ${country eq 'Estonia' ? 'selected="selected"' : ''}>Estonia</option>
<option value='Ethiopia'  ${country eq 'Ethiopia' ? 'selected="selected"' : ''}>Ethiopia</option>
<option value='Falkland Islands'  ${country eq 'Falkland Islands' ? 'selected="selected"' : ''}>Falkland Islands</option>
<option value='Federated States of Micronesia'  ${country eq 'Federated States of Micronesia' ? 'selected="selected"' : ''}>Federated States of Micronesia</option>
<option value='Fiji'  ${country eq 'Fiji' ? 'selected="selected"' : ''}>Fiji</option>
<option value='Finland'  ${country eq 'Finland' ? 'selected="selected"' : ''}>Finland</option>
<option value='France'  ${country eq 'France' ? 'selected="selected"' : ''}>France</option>
<option value='French Guiana'  ${country eq 'French Guiana' ? 'selected="selected"' : ''}>French Guiana</option>
<option value='French Polynesia'  ${country eq 'French Polynesia' ? 'selected="selected"' : ''}>French Polynesia</option>
<option value='Gabon'  ${country eq 'Gabon' ? 'selected="selected"' : ''}>Gabon</option>
<option value='Gaza Strip and West Bank'  ${country eq 'Gaza Strip and West Bank' ? 'selected="selected"' : ''}>Gaza Strip and West Bank</option>
<option value='Georgia'  ${country eq 'Georgia' ? 'selected="selected"' : ''}>Georgia</option>
<option value='Germany'  ${country eq 'Germany' ? 'selected="selected"' : ''}>Germany</option>
<option value='Ghana'  ${country eq 'Ghana' ? 'selected="selected"' : ''}>Ghana</option>
<option value='Gibraltar'  ${country eq 'Gibraltar' ? 'selected="selected"' : ''}>Gibraltar</option>
<option value='Greece'  ${country eq 'Greece' ? 'selected="selected"' : ''}>Greece</option>
<option value='Greenland'  ${country eq 'Greenland' ? 'selected="selected"' : ''}>Greenland</option>
<option value='Grenada'  ${country eq 'Grenada' ? 'selected="selected"' : ''}>Grenada</option>
<option value='Guadeloupe'  ${country eq 'Guadeloupe' ? 'selected="selected"' : ''}>Guadeloupe</option>
<option value='Guam'  ${country eq 'Guam' ? 'selected="selected"' : ''}>Guam</option>
<option value='Guatemala'  ${country eq 'Guatemala' ? 'selected="selected"' : ''}>Guatemala</option>
<option value='Guinea'  ${country eq 'Guinea' ? 'selected="selected"' : ''}>Guinea</option>
<option value='Guinea-Bissau'  ${country eq 'Guinea-Bissau' ? 'selected="selected"' : ''}>Guinea-Bissau</option>
<option value='Guyana'  ${country eq 'Guyana' ? 'selected="selected"' : ''}>Guyana</option>
<option value='Haiti'  ${country eq 'Haiti' ? 'selected="selected"' : ''}>Haiti</option>
<option value='Honduras'  ${country eq 'Honduras' ? 'selected="selected"' : ''}>Honduras</option>
<option value='Hong Kong'  ${country eq 'Hong Kong' ? 'selected="selected"' : ''}>Hong Kong</option>
<option value='Hungary'  ${country eq 'Hungary' ? 'selected="selected"' : ''}>Hungary</option>
<option value='Iceland'  ${country eq 'Iceland' ? 'selected="selected"' : ''}>Iceland</option>
<option value='India'  ${country eq 'India' ? 'selected="selected"' : ''}>India</option>
<option value='Indonesia'  ${country eq 'Indonesia' ? 'selected="selected"' : ''}>Indonesia</option>
<option value='Iran'  ${country eq 'Iran' ? 'selected="selected"' : ''}>Iran</option>
<option value='Iraq'  ${country eq 'Iraq' ? 'selected="selected"' : ''}>Iraq</option>
<option value='Ireland'  ${country eq 'Ireland' ? 'selected="selected"' : ''}>Ireland</option>
<option value='Israel'  ${country eq 'Israel' ? 'selected="selected"' : ''}>Israel</option>
<option value='Italy'  ${country eq 'Italy' ? 'selected="selected"' : ''}>Italy</option>
<option value='Ivory Coast'  ${country eq 'Ivory Coast' ? 'selected="selected"' : ''}>Ivory Coast</option>
<option value='Jamaica'  ${country eq 'Jamaica' ? 'selected="selected"' : ''}>Jamaica</option>
<option value='Japan'  ${country eq 'Japan' ? 'selected="selected"' : ''}>Japan</option>
<option value='Jordan'  ${country eq 'Jordan' ? 'selected="selected"' : ''}>Jordan</option>
<option value='Kazakhstan'  ${country eq 'Kazakhstan' ? 'selected="selected"' : ''}>Kazakhstan</option>
<option value='Kenya'  ${country eq 'Kenya' ? 'selected="selected"' : ''}>Kenya</option>
<option value='Kiribati'  ${country eq 'Kiribati' ? 'selected="selected"' : ''}>Kiribati</option>
<option value='Korea'  ${country eq 'Korea' ? 'selected="selected"' : ''}>Korea</option>
<option value='Kuwait'  ${country eq 'Kuwait' ? 'selected="selected"' : ''}>Kuwait</option>
<option value='Kyrgyzstan'  ${country eq 'Kyrgyzstan' ? 'selected="selected"' : ''}>Kyrgyzstan</option>
<option value='Laos'  ${country eq 'Laos' ? 'selected="selected"' : ''}>Laos</option>
<option value='Latvia'  ${country eq 'Latvia' ? 'selected="selected"' : ''}>Latvia</option>
<option value='Lebanon'  ${country eq 'Lebanon' ? 'selected="selected"' : ''}>Lebanon</option>
<option value='Lesotho'  ${country eq 'Lesotho' ? 'selected="selected"' : ''}>Lesotho</option>
<option value='Liberia'  ${country eq 'Liberia' ? 'selected="selected"' : ''}>Liberia</option>
<option value='Libya'  ${country eq 'Libya' ? 'selected="selected"' : ''}>Libya</option>
<option value='Liechtenstein'  ${country eq 'Liechtenstein' ? 'selected="selected"' : ''}>Liechtenstein</option>
<option value='Lithuania'  ${country eq 'Lithuania' ? 'selected="selected"' : ''}>Lithuania</option>
<option value='Luxembourg'  ${country eq 'Luxembourg' ? 'selected="selected"' : ''}>Luxembourg</option>
<option value='Macau'  ${country eq 'Macau' ? 'selected="selected"' : ''}>Macau</option>
<option value='Macedonia'  ${country eq 'Macedonia' ? 'selected="selected"' : ''}>Macedonia</option>
<option value='Madagascar'  ${country eq 'Madagascar' ? 'selected="selected"' : ''}>Madagascar</option>
<option value='Malawi'  ${country eq 'Malawi' ? 'selected="selected"' : ''}>Malawi</option>
<option value='Malaysia'  ${country eq 'Malaysia' ? 'selected="selected"' : ''}>Malaysia</option>
<option value='Maldives'  ${country eq 'Maldives' ? 'selected="selected"' : ''}>Maldives</option>
<option value='Mali'  ${country eq 'Mali' ? 'selected="selected"' : ''}>Mali</option>
<option value='Malta'  ${country eq 'Malta' ? 'selected="selected"' : ''}>Malta</option>
<option value='Marshall Islands'  ${country eq 'Marshall Islands' ? 'selected="selected"' : ''}>Marshall Islands</option>
<option value='Martinique'  ${country eq 'Martinique' ? 'selected="selected"' : ''}>Martinique</option>
<option value='Mauritania'  ${country eq 'Mauritania' ? 'selected="selected"' : ''}>Mauritania</option>
<option value='Mauritius'  ${country eq 'Mauritius' ? 'selected="selected"' : ''}>Mauritius</option>
<option value='Mayotte'  ${country eq 'Mayotte' ? 'selected="selected"' : ''}>Mayotte</option>
<option value='Mexico'  ${country eq 'Mexico' ? 'selected="selected"' : ''}>Mexico</option>
<option value='Moldova'  ${country eq 'Moldova' ? 'selected="selected"' : ''}>Moldova</option>
<option value='Monaco'  ${country eq 'Monaco' ? 'selected="selected"' : ''}>Monaco</option>
<option value='Mongolia'  ${country eq 'Mongolia' ? 'selected="selected"' : ''}>Mongolia</option>
<option value='Montserrat'  ${country eq 'Montserrat' ? 'selected="selected"' : ''}>Montserrat</option>
<option value='Morocco'  ${country eq 'Morocco' ? 'selected="selected"' : ''}>Morocco</option>
<option value='Mozambique'  ${country eq 'Mozambique' ? 'selected="selected"' : ''}>Mozambique</option>
<option value='Namibia'  ${country eq 'Namibia' ? 'selected="selected"' : ''}>Namibia</option>
<option value='Nauru'  ${country eq 'Nauru' ? 'selected="selected"' : ''}>Nauru</option>
<option value='Nepal'  ${country eq 'Nepal' ? 'selected="selected"' : ''}>Nepal</option>
<option value='Netherlands'  ${country eq 'Netherlands' ? 'selected="selected"' : ''}>Netherlands</option>
<option value='Netherlands Antilles'  ${country eq 'Netherlands Antilles' ? 'selected="selected"' : ''}>Netherlands Antilles</option>
<option value='New Caledonia'  ${country eq 'New Caledonia' ? 'selected="selected"' : ''}>New Caledonia</option>
<option value='New Zealand'  ${country eq 'New Zealand' ? 'selected="selected"' : ''}>New Zealand</option>
<option value='Nicaragua'  ${country eq 'Nicaragua' ? 'selected="selected"' : ''}>Nicaragua</option>
<option value='Niger'  ${country eq 'Niger' ? 'selected="selected"' : ''}>Niger</option>
<option value='Nigeria'  ${country eq 'Nigeria' ? 'selected="selected"' : ''}>Nigeria</option>
<option value='North Korea'  ${country eq 'North Korea' ? 'selected="selected"' : ''}>North Korea</option>
<option value='Northern Mariana Islands'  ${country eq 'Northern Mariana Islands' ? 'selected="selected"' : ''}>Northern Mariana Islands</option>
<option value='Norway'  ${country eq 'Norway' ? 'selected="selected"' : ''}>Norway</option>
<option value='Oman'  ${country eq 'Oman' ? 'selected="selected"' : ''}>Oman</option>
<option value='Pakistan'  ${country eq 'Pakistan' ? 'selected="selected"' : ''}>Pakistan</option>
<option value='Palau'  ${country eq 'Palau' ? 'selected="selected"' : ''}>Palau</option>
<option value='Panama'  ${country eq 'Panama' ? 'selected="selected"' : ''}>Panama</option>
<option value='Papua New Guinea'  ${country eq 'Papua New Guinea' ? 'selected="selected"' : ''}>Papua New Guinea</option>
<option value='Paraguay'  ${country eq 'Paraguay' ? 'selected="selected"' : ''}>Paraguay</option>
<option value='Peru'  ${country eq 'Peru' ? 'selected="selected"' : ''}>Peru</option>
<option value='Philippines'  ${country eq 'Philippines' ? 'selected="selected"' : ''}>Philippines</option>
<option value='Pitcairn Islands'  ${country eq 'Pitcairn Islands' ? 'selected="selected"' : ''}>Pitcairn Islands</option>
<option value='Poland'  ${country eq 'Poland' ? 'selected="selected"' : ''}>Poland</option>
<option value='Portugal'  ${country eq 'Portugal' ? 'selected="selected"' : ''}>Portugal</option>
<option value='Puerto Rico'  ${country eq 'Puerto Rico' ? 'selected="selected"' : ''}>Puerto Rico</option>
<option value='Qatar'  ${country eq 'Qatar' ? 'selected="selected"' : ''}>Qatar</option>
<option value='Reunion'  ${country eq 'Reunion' ? 'selected="selected"' : ''}>Reunion</option>
<option value='Romania'  ${country eq 'Romania' ? 'selected="selected"' : ''}>Romania</option>
<option value='Russia'  ${country eq 'Russia' ? 'selected="selected"' : ''}>Russia</option>
<option value='Rwanda'  ${country eq 'Rwanda' ? 'selected="selected"' : ''}>Rwanda</option>
<option value='Saint Kitts and Nevis'  ${country eq 'Saint Kitts and Nevis' ? 'selected="selected"' : ''}>Saint Kitts and Nevis</option>
<option value='Saint Lucia'  ${country eq 'Saint Lucia' ? 'selected="selected"' : ''}>Saint Lucia</option>
<option value='Saint Pierre and Miquelon'  ${country eq 'Saint Pierre and Miquelon' ? 'selected="selected"' : ''}>Saint Pierre and Miquelon</option>
<option value='Saint Vincent and the Grenadines'  ${country eq 'Saint Vincent and the Grenadines' ? 'selected="selected"' : ''}>Saint Vincent and the Grenadines</option>
<option value='Samoa'  ${country eq 'Samoa' ? 'selected="selected"' : ''}>Samoa</option>
<option value='San Marino'  ${country eq 'San Marino' ? 'selected="selected"' : ''}>San Marino</option>
<option value='Sao Tome and Principe'  ${country eq 'Sao Tome and Principe' ? 'selected="selected"' : ''}>Sao Tome and Principe</option>
<option value='Saudi Arabia'  ${country eq 'Saudi Arabia' ? 'selected="selected"' : ''}>Saudi Arabia</option>
<option value='Scandinavia'  ${country eq 'Scandinavia' ? 'selected="selected"' : ''}>Scandinavia</option>
<option value='Senegal'  ${country eq 'Senegal' ? 'selected="selected"' : ''}>Senegal</option>
<option value='Serbia and Montenegro'  ${country eq 'Serbia and Montenegro' ? 'selected="selected"' : ''}>Serbia and Montenegro</option>
<option value='Seychelles'  ${country eq 'Seychelles' ? 'selected="selected"' : ''}>Seychelles</option>
<option value='Sierra Leone'  ${country eq 'Sierra Leone' ? 'selected="selected"' : ''}>Sierra Leone</option>
<option value='Singapore'  ${country eq 'Singapore' ? 'selected="selected"' : ''}>Singapore</option>
<option value='Slovakia'  ${country eq 'Slovakia' ? 'selected="selected"' : ''}>Slovakia</option>
<option value='Slovenia'  ${country eq 'Slovenia' ? 'selected="selected"' : ''}>Slovenia</option>
<option value='Solomon Islands'  ${country eq 'Solomon Islands' ? 'selected="selected"' : ''}>Solomon Islands</option>
<option value='Somalia'  ${country eq 'Somalia' ? 'selected="selected"' : ''}>Somalia</option>
<option value='South Africa'  ${country eq 'South Africa' ? 'selected="selected"' : ''}>South Africa</option>
<option value='South Korea'  ${country eq 'South Korea' ? 'selected="selected"' : ''}>South Korea</option>
<option value='Spain'  ${country eq 'Spain' ? 'selected="selected"' : ''}>Spain</option>
<option value='Sri Lanka'  ${country eq 'Sri Lanka' ? 'selected="selected"' : ''}>Sri Lanka</option>
<option value='Sudan'  ${country eq 'Sudan' ? 'selected="selected"' : ''}>Sudan</option>
<option value='Suriname'  ${country eq 'Suriname' ? 'selected="selected"' : ''}>Suriname</option>
<option value='Swaziland'  ${country eq 'Swaziland' ? 'selected="selected"' : ''}>Swaziland</option>
<option value='Sweden'  ${country eq 'Sweden' ? 'selected="selected"' : ''}>Sweden</option>
<option value='Switzerland'  ${country eq 'Switzerland' ? 'selected="selected"' : ''}>Switzerland</option>
<option value='Syria'  ${country eq 'Syria' ? 'selected="selected"' : ''}>Syria</option>
<option value='Taiwan'  ${country eq 'Taiwan' ? 'selected="selected"' : ''}>Taiwan</option>
<option value='Tajikistan'  ${country eq 'Tajikistan' ? 'selected="selected"' : ''}>Tajikistan</option>
<option value='Tanzania'  ${country eq 'Tanzania' ? 'selected="selected"' : ''}>Tanzania</option>
<option value='Thailand'  ${country eq 'Thailand' ? 'selected="selected"' : ''}>Thailand</option>
<option value='The Gambia'  ${country eq 'The Gambia' ? 'selected="selected"' : ''}>The Gambia</option>
<option value='The Holy See'  ${country eq 'The Holy See' ? 'selected="selected"' : ''}>The Holy See</option>
<option value='Togo'  ${country eq 'Togo' ? 'selected="selected"' : ''}>Togo</option>
<option value='Tonga'  ${country eq 'Tonga' ? 'selected="selected"' : ''}>Tonga</option>
<option value='Trinidad and Tobago'  ${country eq 'Trinidad and Tobago' ? 'selected="selected"' : ''}>Trinidad and Tobago</option>
<option value='Tunisia'  ${country eq 'Tunisia' ? 'selected="selected"' : ''}>Tunisia</option>
<option value='Turkey'  ${country eq 'Turkey' ? 'selected="selected"' : ''}>Turkey</option>
<option value='Turkmenistan'  ${country eq 'Turkmenistan' ? 'selected="selected"' : ''}>Turkmenistan</option>
<option value='Turks and Caicos Islands'  ${country eq 'Turks and Caicos Islands' ? 'selected="selected"' : ''}>Turks and Caicos Islands</option>
<option value='Tuvalu'  ${country eq 'Tuvalu' ? 'selected="selected"' : ''}>Tuvalu</option>
<option value='Uganda'  ${country eq 'Uganda' ? 'selected="selected"' : ''}>Uganda</option>
<option value='Ukraine'  ${country eq 'Ukraine' ? 'selected="selected"' : ''}>Ukraine</option>
<option value='United Arab Emirates'  ${country eq 'United Arab Emirates' ? 'selected="selected"' : ''}>United Arab Emirates</option>
<option value='United Kingdom'  ${country eq 'United Kingdom' ? 'selected="selected"' : ''}>United Kingdom</option>
<option value='United States Virgin Islands'  ${country eq 'United States Virgin Islands' ? 'selected="selected"' : ''}>United States Virgin Islands</option>
<option value='Uruguay'  ${country eq 'Uruguay' ? 'selected="selected"' : ''}>Uruguay</option>
<option value='Uzbekistan'  ${country eq 'Uzbekistan' ? 'selected="selected"' : ''}>Uzbekistan</option>
<option value='Vanuatu'  ${country eq 'Vanuatu' ? 'selected="selected"' : ''}>Vanuatu</option>
<option value='Venezuela'  ${country eq 'Venezuela' ? 'selected="selected"' : ''}>Venezuela</option>
<option value='Vietnam'  ${country eq 'Vietnam' ? 'selected="selected"' : ''}>Vietnam</option>
<option value='West Bank and Gaza Strip'  ${country eq 'West Bank and Gaza Strip' ? 'selected="selected"' : ''}>West Bank and Gaza Strip</option>
<option value='Western Sahara'  ${country eq 'Western Sahara' ? 'selected="selected"' : ''}>Western Sahara</option>
<option value='Yemen'  ${country eq 'Yemen' ? 'selected="selected"' : ''}>Yemen</option>
<option value='Yugoslavia'  ${country eq 'Yugoslavia' ? 'selected="selected"' : ''}>Yugoslavia</option>
<option value='Zaire'  ${country eq 'Zaire' ? 'selected="selected"' : ''}>Zaire</option>
<option value='Zambia'  ${country eq 'Zambia' ? 'selected="selected"' : ''}>Zambia</option>
<option value='Zimbabwe'  ${country eq 'Zimbabwe' ? 'selected="selected"' : ''}>Zimbabwe</option>
</select>
