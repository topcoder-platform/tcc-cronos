<%response.setContentType("text/xml");
  response.setHeader("Pragma", "no-cache");
  response.setHeader("Cache-Control", "no-cache");
  response.setIntHeader("Expires", -1);
%>
<response>
    <timeLeft>${timeLeft}</timeLeft>
</response>
