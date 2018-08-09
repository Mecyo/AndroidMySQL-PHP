<?php
	$conn = new mysqli("localhost", "root", "alunoifba", "android");
	$sql = "DELETE FROM clientes WHERE id = ?";
	$stm = $conn->prepare($sql);
	$stm->bind_param("i", $_GET["id"]);
	$result = $stm->execute();
	$stm->close();
	$conn->close();
	echo ($result )? "YES" : "FALSE";
?>