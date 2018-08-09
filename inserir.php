<?php
	$conn = new mysqli("localhost", "root", "alunoifba", "android");
	$sql = "INSERT INTO clientes (nome, email) VALUES (?, ?)";
	$stm = $conn->prepare($sql);
	$stm->bind_param("ss", $_GET["nome"], $_GET["email"]);
	$result = $stm->execute();
	$stm->close();
	$conn->close();
	echo ($result )? "YES" : "FALSE";
?>