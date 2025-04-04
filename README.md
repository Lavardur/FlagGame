# HBV201G Lokaverkefni Vor 2025
## Fánaleikur (Flag Game)

Fánaleikur þar sem þú færð fána og átt að velja hvaða land tilheyrir fánanum. Leikurinn býður upp á þrjú erfiðleikastig og leyfir þér að velja hvaða heimshluta þú vilt giska út frá.

## Uppsetning

### Forritunarkröfur

Áður en þú byrjar, gakktu úr skugga um að þú hafir eftirfarandi uppsett:

- **Java Development Kit (JDK) 17 eða nýrri**
- **Apache Maven**
- **Git**

### Skref 1: Klónaðu verkefnið

Keyrðu eftirfarandi skipun til að klóna verkefnið af GitHub:

```bash
git clone https://github.com/Lavardur/vidmotsforritun
```

### Skref 2: Fara í verkefnamöppuna

Farðu í möppuna sem þú klónaðir:

```bash
cd vidmotsforritun/Verkefni10
```

### Skref 3: Byggja verkefnið

Notaðu Maven til að byggja verkefnið:

```bash
mvn clean package
```

### Skref 4: Keyra verkefnið

Keyrðu verkefnið með Maven:

```bash
mvn javafx:run
```

## Spilun

1. Á upphafsskjánum, smelltu á **"Start Game"**.
2. Veldu stillingar:
    - **Continent**: Veldu heimsálfu eða "All Continents".
    - **Difficulty**:
      - **Easy**: Þekktustu fánar með 3 valmöguleikum.
      - **Medium**: Allir fánar með 4 valmöguleikum.
      - **Hard**: Allir fánar með 5 valmöguleikum.
3. Smelltu á **"Start Game"** til að byrja.

### Fyrir hvern fána:

- Veldu svar úr valmöguleikunum.
- Smelltu á **"Submit"** til að athuga svarið.
- Smelltu á **"Next Flag"** til að halda áfram.

### Lokaútkomur:

Eftir 10 spurningar munt þú sjá lokaútkomu leiksins.