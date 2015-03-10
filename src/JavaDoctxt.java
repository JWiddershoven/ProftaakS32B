IllegalArgumentException

/**
     er wordt afhankelijk van het geslacht een man/vrouw gecreeerd met als voornamen vnamen, achternaam anaam, tussenvoegsel tvoegsel, geboortedatum     gebdat en ouderlijke relatie ouders; 
	van elke voornaam en achternaam zijn zo nodig de eerste letter naar een hoofdletter en de andere letters naar kleine letters getransformeerd; 
	voor het tussenvoegsel zijn alle letters zo nodig naar een kleine letter getransformeerd; 
	de persoon krijgt een uniek nummer toegewezen
	deze persoon staat voortaan ook als kind bij ouders, mits bekend, geregistreerd;
@param geslacht
@param vnamen alle voornamen zijn niet-lege strings en er is er ten minste 1
@param anaam achternaam mag geen lege string zijn
@param tvoegsel mag een lege string zijn (dan is er geen tussenvoegsel)
@param gebdat
@param ouders mag null zijn indien ouders onbekend
**/
public void addPersoon(
	Geslacht geslacht, 
	String[] vnamen, 
	String anaam, 
	String tvoegsel,
	GregorianCalendar gebdat,
	Relatie ouders) 


Wat ga je testen?
- Precondities
- Postcondities
- Returnwaarde
- Excepties

/* voor het tussenvoegsel zijn alle letters zo nodig naar een kleine letter getransformeerd */

Normale waarden: “Van”, “der”
Extreme waarden: , “DER”, “VAn dEr”


TestExceptie
@Test 
(expected = IllegalArgumentException.class)
void testLegeAchternaam() {
/* achternaam mag geen lege string zijn */

Persoon p = adm.addPersoon(Geslacht.MAN,
	{"Piet”}, "", "op", vandaag, null);

}

TestExceptie(2e versie)
@Test 
void testAddPersoon() {

	/* achternaam mag geen lege string zijn */
	try {
	Persoon p = adm.addPersoon(Geslacht.MAN,
		{"Piet”}, "", "op", vandaag, null);
		fail(“achternaam mag geen lege string zijn”);
	} catch (IllegalArgumentException exc) {
	}
}



