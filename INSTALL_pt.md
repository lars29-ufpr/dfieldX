# Guia de Instalação e Execução

## Requisitos do Sistema
- Ambiente de Execução Java (JRE) versão 17 ou superior (Java 21 recomendado).
- Sistemas Operativos Suportados:
  - Linux (Ubuntu, Fedora, Arch Linux, Mint, etc.)
  - Windows 10 / 11
  - macOS (Intel & Apple Silicon)

## Como Executar a Aplicação Pré-empacotada (.jar)
Para executar a aplicação diretamente sem compilar a partir do código-fonte, certifique-se de que o Java está instalado e execute no terminal ou linha de comandos:

java -jar target/dfieldX.jar

### Notas Específicas por Sistema Operativo:
- Linux: Certifique-se de que o seu ambiente de trabalho suporta renderização gráfica JavaFX (bibliotecas GTK3 instaladas). Abra o terminal no diretório da aplicação e execute o comando.
- Windows: Abra o Prompt de Comando (cmd) ou o PowerShell na pasta da aplicação e execute o comando. Certifique-se de que o Java está registado no PATH do sistema.
- macOS: Abra o Terminal, navegue até à pasta da aplicação com o comando cd e execute o comando. Verifique se o ambiente Java está devidamente configurado.

## Forçando um Idioma Específico via Linha de Comando
Pode alterar o idioma da interface independentemente do padrão do sistema operativo utilizando propriedades do sistema:

- Português (Brasil):
  java -Duser.language=pt -Duser.country=BR -jar target/dfieldX.jar
- Inglês:
  java -Duser.language=en -Duser.country=US -jar target/dfieldX.jar
- Espanhol:
  java -Duser.language=es -Duser.country=ES -jar target/dfieldX.jar
- Francês:
  java -Duser.language=fr -Duser.country=FR -jar target/dfieldX.jar
- Russo:
  java -Duser.language=ru -Duser.country=RU -jar target/dfieldX.jar

## Desenvolvimento e Compilação a partir do Código-Fonte
Caso pretenda compilar e empacotar a aplicação a partir do código-fonte utilizando o Maven:
mvn clean package
