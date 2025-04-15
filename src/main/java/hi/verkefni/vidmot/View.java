/******************************************************************************
 *  Nafn    : Anton Benediktsson
 *  T-póstur: anb59@hi.is
 *  Lýsing  : Abstrakt klasi sem skilgreinir grunnvirkni fyrir viðmótshluta í forriti.
 *            Inniheldur aðferð til að sækja rótina (root) og stilla hana.
 *****************************************************************************/

package hi.verkefni.vidmot;

import javafx.scene.Parent;

public abstract class View {
    protected Parent root;

    public abstract Parent getRoot();

    public void setRoot(Parent root) {
        this.root = root;
    }
}