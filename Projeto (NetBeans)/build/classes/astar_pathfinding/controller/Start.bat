@ECHO off
CLS
IF NOT [JAVA]==[] (
    IF EXIST Start.java (
        echo Iniciando aplicacao...
	javac -jar Start.jar
	echo Executando aplicacao...
	java Pathfinding
    ) ELSE (
        echo O arquivo Start.jar nao foi encontrado nesta pasta! Nao e possivel executar o aplicativo.
    )
) ELSE (
    echo ERRO: O Java nao faz parte das variaveis de ambiente. Nao e possivel executar o aplicativo.
)
PAUSE