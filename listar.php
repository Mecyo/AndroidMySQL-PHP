<?php
	$conn = new mysqli("localhost", "root", "alunoifba", "android");
	$myJson = array();
    foreach ($conn->query("SELECT * FROM clientes") as $row) {
		$array = array("id" => intval($row['id']),
				"nome" => $row['Nome'],
				"email" => $row['Email']
			);
        array_push( $myJson, $array );
    }
	$conn->close();
	echo json_encode($myJson); exit;
?>