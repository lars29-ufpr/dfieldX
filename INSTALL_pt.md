[English Version](INSTALL.md)

# Guia de Instalação

# dfieldX e pplaneX

Este guia explica como instalar e executar o dfieldX e o pplaneX nos sistemas Windows, macOS e Linux.

Esses programas exigem Java 21 ou superior.

Nenhuma instalação prévia do Java é necessária. Este guia assume que o Java ainda não está instalado em seu computador.

---

# 1. Baixando o Programa

Faça o download da versão mais recente no GitHub:

- dfieldX: https://tinyurl.com/difieldx-ufpr
- pplaneX: https://github.com/luiz-santana/pplane-java

Salve o arquivo JAR baixado em um local fácil de encontrar, como a Área de Trabalho ou a pasta Downloads.

---

# 2. Windows

## Instalar o Java

1. Abra seu navegador.
2. Acesse: https://adoptium.net/
3. Baixe o Eclipse Temurin Java 21 LTS para Windows x64.
4. Execute o instalador baixado.
5. Na tela "Custom Setup", garanta que "Add to PATH" e "Associate .jar files" estejam marcados.
6. Conclua a instalação.

## Verificar a Instalação

Abra o Prompt de Comando e execute:

    java -version

Qualquer versão 21 ou superior é suportada.

## Executar o Programa

    java -jar dfieldX-1.0.0.jar

ou

    java -jar pplaneX.jar

---

# 3. macOS

## Identifique o Tipo do seu Mac

Abra “Sobre Este Mac”.

Escolha a versão Apple Silicon para Macs com processadores M1, M2, M3, M4 ou superiores, e a versão Intel para Macs Intel.

## Instalar o Java

1. Acesse https://adoptium.net/
2. Baixe o Java 21 LTS para macOS.
3. Execute o instalador.
4. Siga as instruções na tela.

## Verificar a Instalação

Abra o Terminal e execute:

    java -version

## Executar o Programa

    java -jar dfieldX-1.0.0.jar

ou

    java -jar pplaneX.jar

## Se o macOS Bloquear a Aplicação

Abra:

Ajustes do Sistema → Privacidade e Segurança → Abrir Mesmo Assim

---

# 4. Linux (Ubuntu, Debian e Linux Mint)

Instalar Java:

    sudo apt update
    sudo apt install openjdk-21-jre

Verificar:

    java -version

Executar:

    java -jar dfieldX-1.0.0.jar

ou

    java -jar pplaneX.jar

---

# 5. Linux (Fedora, Rocky Linux e AlmaLinux)

Instalar Java:

    sudo dnf install java-21-openjdk

Verificar:

    java -version

Executar:

    java -jar dfieldX-1.0.0.jar

ou

    java -jar pplaneX.jar

---

# 6. Linux (Arch Linux e Manjaro)

Instalar Java:

    sudo pacman -S jre21-openjdk

Verificar:

    java -version

Executar:

    java -jar dfieldX-1.0.0.jar

ou

    java -jar pplaneX.jar

---

# Solução de Problemas

## Nada Acontece ao Dar Duplo Clique no Arquivo JAR

Execute o programa pelo terminal:

    java -jar dfieldX-1.0.0.jar

ou

    java -jar pplaneX.jar

## Erro de Versão da Classe (Unsupported Class Version)

Instale o Java 21 ou superior.

## O Programa Não Inicia

Verifique:

    java -version

E informe ao desenvolvedor qualquer mensagem de erro apresentada.

---

# Informações Adicionais

O dfieldX e o pplaneX são distribuídos como arquivos JAR executáveis independentes.

Após instalar o Java 21 ou superior, nenhuma instalação adicional é necessária.

Basta manter o arquivo JAR em uma pasta de sua preferência e executá-lo sempre que desejar.

Aproveite a exploração de equações diferenciais e sistemas dinâmicos!

---

## 🍎 Aviso de Segurança no macOS (Apple Gatekeeper)

Se ao abrir o arquivo `.jar` surgir um aviso informando que **"a Apple não pôde verificar se o arquivo está livre de malware"**, isso ocorre porque o software é livre e não possui uma assinatura comercial paga da Apple.

### Método 1: Ajustes do Sistema (Interface Gráfica)
1. Clique em **Concluído** (Done) na janela de aviso.
2. Abra **Ajustes do Sistema** (System Settings) → **Privacidade e Segurança** (Privacy & Security).
3. Role até a seção **Segurança** (Security).
4. Localize o aviso sobre o bloqueio do arquivo `.jar` e clique em **Abrir Mesmo Assim** (Open Anyway).
5. Confirme com sua senha/Touch ID e clique em **Abrir**.

### Método 2: Terminal
Remova o atributo de quarentena do macOS executando:
```bash
xattr -d com.apple.quarantine *.jar
```
Ou execute o programa diretamente pelo terminal:
```bash
java -jar *.jar
```
