<?php
	$conn = new mysqli("localhost", "root", "alunoifba", "android");
	$sql = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?";
	$stm = $conn->prepare($sql);
	$stm->bind_param("ssi",$_GET["nome"], $_GET["email"], $_GET["id"]);
	$result = $stm->execute();
	$stm->close();
	$conn->close();
	echo ($result )? "YES" : "FALSE";
?>