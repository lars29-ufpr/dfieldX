> **Language / Idioma:** [English](INSTALL.md) | **Português** | [Español](INSTALL_es.md) | [Français](INSTALL_fr.md) | [Русский](INSTALL_ru.md) | [Български](INSTALL_bg.md) | [Українська](INSTALL_uk.md) | [Deutsch](INSTALL_de.md) | [Italiano](INSTALL_it.md)

# Guia de Instalação

# dfieldX e pplaneX

Este guia explica como instalar e executar o dfieldX e o pplaneX no Windows, macOS e Linux.

Estes aplicativos exigem o Java 21 ou mais recente.

Não é necessária nenhuma instalação anterior do Java. Este guia pressupõe que o Java ainda não está instalado no seu computador.

---

# 1. Baixar o Aplicativo

Baixe a versão mais recente do GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr

- pplaneX: https://tinyurl.com/pplanex-ufpr


Salve o arquivo JAR baixado em um local que você possa encontrar facilmente depois, como a sua Área de Trabalho (Desktop) ou a pasta Downloads.

---

# 2. Windows

## Instalar o Java

1. Abra o seu navegador.
2. Acesse: https://adoptium.net/
3. Baixe o Eclipse Temurin Java 21 LTS para Windows x64.
4. Execute o instalador baixado.
5. Em "Custom Setup" (Instalação Personalizada), certifique-se de que "Add to PATH" e "Associate .jar files" estejam habilitados.
6. Conclua a instalação.

## Verificar a Instalação

Abra o Prompt de Comando e execute:

    java -version

Qualquer versão 21 ou mais recente é suportada.

## Executar o Aplicativo

    java -jar dfieldX.jar

ou

    java -jar pplaneX.jar

---

# 3. macOS

## Instalar o Java
1. Acesse https://adoptium.net/
2. Baixe o Java LTS mais recente (21 ou superior) para macOS.  
   * *Dica:* Escolha o instalador `.dmg` (ex: `jdk-21_macos-x64_bin.dmg` para Macs com Intel, ou o equivalente `aarch64` para Apple Silicon M1/M2/M3/M4).
3. Execute o instalador e siga as instruções na tela.

## Organizar o Aplicativo
Para uma melhor organização, recomendamos mover os arquivos baixados `dfieldX.jar` e `pplaneX.jar` para a sua pasta **Aplicativos** (Applications).

## Primeira Execução (Contornando o Gatekeeper)
O macOS possui um recurso de segurança que bloqueia aplicativos de desenvolvedores não identificados. Se você simplesmente der um clique duplo no arquivo `.jar` pela primeira vez, o macOS exibirá um aviso e impedirá a abertura.

1. **Não dê clique duplo** no arquivo na primeira vez.
2. Em vez disso, clique com o **botão direito** (ou Control-clique) no arquivo `.jar` e selecione **Abrir** (Open) no menu de contexto.
3. Uma caixa de diálogo aparecerá pedindo confirmação. Clique em **Abrir** novamente.
4. **Uso Normal:** Você só precisa fazer isso uma vez. Para todos os usos subsequentes, basta dar um clique duplo no arquivo `.jar` para iniciar o aplicativo normalmente.

## Executar via Terminal (Alternativa)
Se preferir a linha de comando, abra o Terminal e execute:
    java -jar dfieldX.jar
ou
    java -jar pplaneX.jar

---

# 4. Linux (Ubuntu, Debian, Linux Mint)

Instalar o Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Verificar:

    java -version

Executar:

    java -jar dfieldX.jar

ou

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux, AlmaLinux)

Instalar o Java:

    sudo dnf install java-21-openjdk

Verificar:

    java -version

Executar:

    java -jar dfieldX.jar

ou

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux, Manjaro)

Instalar o Java:

    sudo pacman -S jre21-openjdk

Verificar:

    java -version

Executar:

    java -jar dfieldX.jar

ou

    java -jar pplaneX.jar

---

# Solução de Problemas

## Nada Acontece ao Dar Clique Duplo no Arquivo JAR

Execute o aplicativo por um terminal:

    java -jar dfieldX.jar

ou

    java -jar pplaneX.jar

## Erro "Unsupported Class Version"

Instale o Java 21 ou mais recente.

## O Aplicativo Não Inicia

Verifique:

    java -version

e reporte quaisquer mensagens de erro ao desenvolvedor.
