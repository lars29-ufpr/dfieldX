# dfieldX (Versão 1.1)

Aplicação desktop em **JavaFX** desenvolvida para a visualização gráfica de campos de direções, análise de equações diferenciais ordinárias (EDO) e exploração de modelos matemáticos aplicados.

---

## 🌍 Internacionalização (i18n & L10n)
O dfieldX possui suporte nativo completo para 5 idiomas, adaptando-se automaticamente ao idioma do sistema operativo ou permitindo a seleção via linha de comando:
- 🇧🇷 Português (Brasil) (pt_BR)
- 🇺🇸 Inglês (EUA) (en)
- 🇪🇸 Espanhol (es)
- 🇫🇷 Francês (fr)
- 🇷🇺 Russo (ru)

---

## 🎨 Principais Funcionalidades
- **Galeria Didática de EDOs:** Navegação intuitiva por categorias (Biomatemática, Física, Química, Matemática e Modelos Personalizados) com painel de inspeção detalhado contendo equações, variáveis, parâmetros e janelas de visualização sugeridas.
- **Salvamento de Modelos:** Permite ao utilizador guardar facilmente o estado atual da EDO como um modelo personalizado diretamente na galeria.
- **Interface Estabilizada:** Layout refinado sem truncamentos, botões optimizados e suporte a temas visuais modernos.

---

## 🚀 Como Executar (Apenas JRE)
Para executar a aplicação diretamente através de um ambiente de execução Java (JRE), sem a necessidade de ferramentas de compilação como o Maven na máquina do utilizador final:

java -jar target/dfieldX.jar

### 🌐 Forçando um Idioma Específico via Linha de Comando
Para iniciar a aplicação num idioma específico, independentemente da configuração padrão do sistema operativo, utilize os parâmetros de Locale:

- Português (Brasil):
  java -Duser.language=pt -Duser.country=BR -jar target/dfieldX.jar

- Francês:
  java -Duser.language=fr -Duser.country=FR -jar target/dfieldX.jar

- Espanhol:
  java -Duser.language=es -Duser.country=ES -jar target/dfieldX.jar

- Inglês:
  java -Duser.language=en -Duser.country=US -jar target/dfieldX.jar

- Russo:
  java -Duser.language=ru -Duser.country=RU -jar target/dfieldX.jar

---

## 🛠️ Como Compilar e Empacotar (Desenvolvimento)
Para gerar uma nova versão do executável empacotado (dfieldX.jar) a partir do código-fonte:

mvn clean package
