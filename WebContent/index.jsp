<%@page contentType="text/html" pageEncoding="UTF-8"%>

	<script src="js/jquery-3.5.1.min.js"></script>
	<script src="js/font-awesome.js"></script> 

<!DOCTYPE html>
<html>
 
<head>
	<title>Login Medix</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.4.1.3.min.css">
	<link rel="stylesheet" type="text/css" href="css/login.css">
	
	<link rel="stylesheet" type="text/css" href="./css/jquery-confirm-3.3.2.min.css">
	<script src="./js/jquery-confirm-3.3.2.min.js"></script> 

</head>

<body>
	<div class="container h-100">
		<div class="d-flex justify-content-center h-100">
			<div class="user_card">
				<div class="d-flex justify-content-center">
					<div class="brand_logo_container">
					 	<img src="img/logo.png" class="brand_logo" alt="Logo">
					</div>
				</div>
				
				<%  HttpSession ses = request.getSession(true);
					String login = (String)ses.getAttribute("loginIncorreto");
					login = (login == null ? "N" : login);
										
					if(login.equals("S")){ %>
						<script type="text/javascript" language="JavaScript">
							$.confirm({
							    title: 'Atenção!',
							    content: 'Login e/ou senha inválida!',
							    type: 'red',
							    typeAnimated: true,
							    buttons: {
							        tryAgain: {
							            text: 'Ok',
							            btnClass: 'btn-red',
							            action: function(){
							            }
							        }
							    }
							})
						</script>
			    	<%}%>
								
				<div class="d-flex justify-content-center form_container">
					<form method="post" action="/medix/auth">
					
						<div class="input-group mb-3">
							<div class="input-group-append">
								<span class="input-group-text"><i class="fas fa-user"></i></span>
							</div>
							<input type="text" name="login" class="form-control input_user" required="required" value="${param.login}" placeholder="login">
						</div>
						
						<div class="input-group mb-2">
							<div class="input-group-append">
								<span class="input-group-text"><i class="fas fa-key"></i></span>
							</div>
							<input type="password" name="senha" class="form-control input_pass" required="required" value="" placeholder="senha">
						</div>

						<div class="d-flex justify-content-center mt-3 login_container">
				 			<button type="submit" name="bLogin" class="btn login_btn">Login</button>
				   		</div>
				   		
					</form>
				</div>

			</div>
		</div>
	</div>
			
</body>
</html>
