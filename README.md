# Teksto Redaktoriaus Aplikacija

Šis projektas įgyvendina paprastą teksto redaktorių su pagrindinėmis funkcijomis ir dviem specializuotais redaktorių tipais: **Vertimo Redaktoriumi** ir **Rašybos Tikrinimo Redaktoriumi**. Aplikacija turi grafinę vartotojo sąsają (GUI), sukurtą naudojant Swing, leidžiančią vartotojams sąveikauti su redaktoriumi, išsaugoti ir įkelti jo būseną bei persijungti tarp redaktorių tipų.

### Funkcijos:

* **Pagrindinis Teksto Redagavimas**:
  * Pridėti tekstą į redaktorių.
  * Rasti ir pakeisti tekstą.
  * Atstatyti redaktoriaus turinį.
* **Vertimo Redaktorius**:
  * Palaiko anglų-lietuvių žodžių porų žodyną.
  * Pridėti naujas žodžių poras į žodyną.
  * Versti redaktoriaus tekstą iš anglų į lietuvių arba atvirkščiai.
* **Rašybos Tikrinimo Redaktorius**:
  * Palaiko pasirinktinį žodyną rašybos tikrinimui.
  * Pridėti žodžius į žodyną.
  * Tikrinti redaktoriaus teksto rašybą, pakeičiant neteisingai parašytus žodžius artimiausiais atitikmenimis (skiriasi vienu simboliu).
* **Išsaugojimas**:
  * Išsaugoti dabartinę redaktoriaus būseną (įskaitant jo tipą ir specifinius duomenis, tokius kaip vertimo žodžiai ar žodynas) į dvejetainį failą (`editor_state.bin`).
  * Įkelti redaktoriaus būseną iš išsaugoto failo paleidžiant aplikaciją.
* **GUI**:
  * Swing pagrindu sukurta grafinė sąsaja.
  * Leidžia pasirinkti, ar kurti naują redaktorių, ar įkelti esamą paleidžiant.
  * Dinamiškai pritaiko mygtukus ir funkcijas pagal aktyvų redaktoriaus tipą.
  * Rodo redaktoriaus turinį ir leidžia įvesti tekstą.
  * Atskiri dialogai žodžiams įtraukti į vertimo ar rašybos tikrinimo žodynus.
* **Daugiagijis (Multithreading)**:
  * Išsaugojimo ir įkėlimo operacijos atliekamos atskirose gijose (`SaveThread` ir `LoadThread`), siekiant išlaikyti GUI reagavimą.

### Projekto Struktūra:

* `editor/`:
  * `Editor.java`: Pagrindinė visų redaktorių klasė, teikianti pagrindines teksto manipuliavimo funkcijas ir `Serializable` palaikymą išsaugojimui.
  * `TranslateEditor.java`: Išplečia `Editor` klasę, pridėdama vertimo galimybes ir tvarkanti anglų-lietuvių žodžių sąrašus.
  * `SpellCheckEditor.java`: Išplečia `Editor` klasę, pridėdama rašybos tikrinimo galimybes ir tvarkanti žodyną. Įgyvendina `Cloneable`.
  * `EditorCreator.java`: Sąsaja, skirta redaktorių egzemplioriams kurti.
  * `TranslateEditorCreator.java`: Įgyvendina `EditorCreator` vertimo redaktoriui.
  * `SpellCheckEditorCreator.java`: Įgyvendina `EditorCreator` rašybos tikrinimo redaktoriui.
  * `LoadThread.java`: `Thread` poklasis, atsakingas už `Editor` būsenos įkėlimą iš failo.
  * `SaveThread.java`: `Thread` poklasis, atsakingas už `Editor` būsenos išsaugojimą į failą.
* `EditorGUI.java`: Pagrindinė klasė, kuri nustato ir valdo grafinę vartotojo sąsają redaktoriui.

### Kaip Paleisti:

1. **Kompiliuoti**: Sukompiliuokite visus Java failus. Jei esate projekto šakniniame kataloge, galite naudoti:
   ```bash
   javac editor/*.java EditorGUI.java
   ```
2. **Paleisti**: Paleiskite `EditorGUI` klasę:
   ```bash
   java EditorGUI
   ```

### Naudojimas:

1. Paleidus aplikaciją, pasirodys dialogas, raginantis pasirinkti „Create Editor“ (pradėti su nauju, tuščiu bendruoju redaktoriumi) arba „Load Editor“ (įkelti anksčiau išsaugotą būseną iš `editor_state.bin`).
2. **Įvesties laukas**: Įveskite tekstą į viršuje esantį įvesties lauką ir paspauskite Enter, kad pridėtumėte jį prie pagrindinio redaktoriaus rodymo srities.
3. **Mygtukai**:
   * **Save**: Išsaugo dabartinę redaktoriaus būseną į `editor_state.bin`.
   * **Reset**: Išvalo redaktoriaus tekstą ir, specializuotų redaktorių atveju, jų atitinkamus duomenis (vertimo žodžius ar žodyną).
   * **Find and Replace**: Prašo įvesti ieškomą tekstą ir tekstą, kuriuo jį pakeisti.
   * **Switch Editor**: Perjungia tarp `TranslateEditor` ir `SpellCheckEditor`. Mygtukai atitinkamai pasikeis.
   * **Add Word (Translate Editor)**: Atidaro dialogą anglų-lietuvių žodžių poroms pridėti.
   * **Translate (Translate Editor)**: Verčia tekstą redaktoriuje, atsižvelgiant į „Translate to Lithuanian“ žymės langelį.
   * **Translate to Lithuanian (Žymės langelis)**: Nustato vertimo kryptį (į lietuvių kalbą, jei pažymėta; į anglų, jei nepažymėta).
   * **Add Word (Spell Check Editor)**: Atidaro dialogą žodžiams į rašybos tikrinimo žodyną pridėti.
   * **Spell Check (Spell Check Editor)**: Atlieka redaktoriaus teksto rašybos tikrinimą, naudojant dabartinį žodyną.

### Išsaugojimas:

Aplikacija išsaugo ir įkelia visą `Editor` objektą (ir jo specifinės poklasės egzempliorių) naudodama Java objektų serializaciją. Būsena išsaugoma faile `editor_state.bin` tame pačiame kataloge, kuriame paleidžiama aplikacija.
