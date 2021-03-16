<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
    <title>Medix</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link rel="stylesheet" type="text/css" href="css/bootstrap.4.1.3.min.css">

</head>

	<body class="sb-nav-fixed" > 
		

    
		<div id="layoutSidenav_content">
        	<br>
        	<form id=formCadUsuario method="POST" action="/medix/index">
			<main>
				<div class="container-fluid">
				
					<div class="form-row">
						<div class="col-md-4 mb-3">
							<label>IP da base</label> 
							<input type="text" class="form-control" name="ipBase"  value="">
						</div>

						<div class="col-md-4 mb-3">
							<label>Base</label> 
							<input type="text" class="form-control" name="baseDados" value="postgres">
						</div>
					</div>
					
					<div class="form-row">
						 <div class="col-md-4 mb-3">
							<label>Usuário</label> 
							<input type="text" class="form-control"  name="usuarioBase" value="postgres">
						</div>
						<div class="col-md-4 mb-3">
							<label>Senha</label> 
							<input type="password" class="form-control" name="senhaBase" value="postgres">
						</div>
					</div>
					
					<button class="btn btn-primary" name="bSetBase" value="S" type="submit">Salvar</button>
				</div>
			</main>
			</form>
		</div> 
          		
        <%@include file="/menu/scripts.jsp"%>
    </body>
</html>