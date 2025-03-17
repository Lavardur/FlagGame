2025 Verkefni 5 - Viðburðarstjóri
Námsmarkmið
Að nemendur geti:

forritað sérhæfðan klasa
forritað ýmis konar menus (valmyndir)
forritað DatePicker og ComboBox 
forritað spilun video
forritað include

Verkefnið sem á að leysa

Forrita á viðburðarstjóra sem leyfir notanda að skrá viðburði, heiti þeirra, tíma og dagsetningu, flokk og kynningarmyndband. Hægt er að stofna nýjan viðburð, opna viðburð, loka viðburði, breyta þeim, vista og eyða. Einnig er hægt að fá upplýsingar um forritið. Þetta er dæmigert svokallað CRUD (Create, Retrieve, Update, Delete) verkefni. Þegar talað er um að Vista, Nýr, Opna þá er átt við vista í safni af viðburðum í minni en ekki á diski eða í gagnagrunni. Það er fyrir utan námsefni námskeiðsins en þið lærið um seinna í náminu. 

Dæmablöð vika 8 og vika 9 

Myndband 
Panopto upptakaLinks to an external site.  frá 3.3 minútu 54 ca

Mynd

Viðburðarstjori.png

Gagnvirkniskröfur
Í gegnum valmynd (menu) á valmyndarslá á að vera hægt að: 

1. Nýr: Búa til nýjan viðburð 
2. Opna: Opna viðburð eftir heiti sem slegið er inn í TextInputDialog og birta í núverandi glugga 
3. Vista: Vista viðburð 
4. Loka: Loka viðburðinum, viðburðurinn hverfur af skjánum. 
5. Um: Fá upplýsingar um forritið í einföldum upplýsingadialog 
6. Hætta: Hætta í forriti


Í Viðburðarglugga (ekki dialog) á að vera hægt að skrá eftirfarandi upplýsingar

7.  Heiti viðburðar
8.  Dagsetning viðburðar, valinn með DatePicker 
9.  Klukkan hvað viðburður byrjar (kls) 
10.  Flokkur viðburðar, valinn með ComboBoxi, t.d. Fræðsla, Fjölskylda, Skemmtun  
11. Kynningarmyndband, valið úr skrá. Myndband spilast í glugga 

Útlitskröfur 
Vandið hönnun útlitsins. Gætið t.d. að bili á milli viðmótshluta, stærð viðmótshluta og útliti.
Farið eftir venjum um röðun á valmynd 
Farið eftir hönnunarleiðbeiningum 
Forritunarkröfur
Hafið tvo pakka (e. package) hi.verkefni.vidmot og hi.verkefni.vinnsla (eða xxx.yyy.vidmot og xxx.yyy.vinnsla má líka vera bara vidmot og vinnsla)... og allt þetta góða 
Forritið Viðburð (EventView)  sem sérhæfðan klasa (custom components) (í event-view.fxml)
Setjið MenuBar í sér .fxml skrá (menu-view.fxml), hafið sér controller fyrir menu stýringuna  og notið <fx:include í aðal .fxml skránni (eventManager-view.fxml) 
Notið DatePicker fyrir dagatalið 
Notið ComboBox fyrir val á viðburðaflokkum
Lýsið notendaviðmóti í .fxml skrám 
Lýsið stílum í .css skrá, events.css
Það fer vel á að setja Kynningarmyndbandshlutann í EventView í sér .fxml skrá sem er include-d í aðalglugganum og láta hann hafa sér controller. Þetta er valkvætt. 
Notist við góðar forritunarvenjur. Hafið aðferðir stuttar. Forðist endurtekinn kóða. Hjálparaðferðir eiga að vera private. Allar tilviksbreytur eiga að vera private og hafa get og set-aðferðir ef á þarf að halda.
Hafið klasa með stórum upphafsstaf og breytur og aðferðir með litlum upphafsstaf.
Skjalið klasana, tilviksbreytur og aðferðir með JavaDocLinks to an external site. aðferð.
Gagnleg dæmi
Vika 3 - StackPane 
Vika 6   Serhaefdur, og Spil21 (2021) og Snake (2022)
Vika 7 - Valmyndir, ValmyndirInclude og TveirControllerar og Verkefni 3 frá 2021
Vika 7 - ComboboxDyr og DagatalDatePicker


Klasarnir
Hér er FXML_Lestur Download FXML_Lesturklasi sem þið getið notað til að lesa inn .fxml skrár sem hafa sérhæfða klasa (custom component)

Hafið til hliðsjónar eftirfarandi klasa. Ef þið notið aðra klasa gerið grein fyrir þeim. 

viðmótið
EventManagerApplicatiaon
EventManagerController
EventView (Sérhæfður klasi með eigin controller)
KynningController (valkvætt, sér klasi fyrir media-view.fxml)
MenuController (sér klasi fyrir menu-view.fxml) valkvætt að hafa sérklasa fyrir menu-view.fxml  

vinnsla
EventModel
Flokkur

fxml 
event-view.fxml
eventmanager-view.fxml
media-view.fxml (valkvætt, getur verið hluti af event-view.fxml) 
menu-view.fxml

css
eventmanager.css

Námsmat
Það er gefin einkunn fyrir útlit,  gagnvirkni og forritun. Ef forrit þýðir (e. compile) ekki fæst ekkert fyrir gagnvirkni og forritun. Gætið þess að prófa forritið vel þannig að það krassi ekki.  Verkefnið er einstaklingsverkefni. Forritið það sjálf. 
Hér er matskvarði Download matskvarðifyrir verkefnið. Með fyrirvara um hugsanlegar breytingar. Hér er gagnvirkur matskvarði Download gagnvirkur matskvarði.

Skila

Skilaðu í Gradescope. Sjáðu leiðbeiningar hér.

Lausnin 
Lausnin verður birt á https://github.com/Hvannberg/HBV201G-forrit