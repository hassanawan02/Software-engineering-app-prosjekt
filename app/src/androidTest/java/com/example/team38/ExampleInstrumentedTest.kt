package com.example.team38

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.team38.screens.OmOss
import com.example.team38.screens.SkrivTekst
import org.junit.Test
import org.junit.Assert.*


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class  ExampleInstrumentedTest {



    @Test
    fun testSkrivTekst(){
        val rule = createComposeRule()

        rule.setContent { SkrivTekst() }

        rule.onNodeWithText("Appen viser dagens værdata og strømpris for dagen slik at brukeren kan se sammenhenger mellom disse variablene. På denne måten kan brukeren selv tenke seg hvordan strømprisene vil variere basert på værprognosene.\n")
            .assertExists()

    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.team38", appContext.packageName)
    }





    @Test
    fun testContentOmOssScreen() {

        val rule = createComposeRule()


        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        rule.setContent {
            MyAppNavHost(navController = navController)
            OmOss({  navController.navigate("home")},  {navController.navigate("instillinger")  }, {  navController.navigate("strompris") },{ navController.navigate("resultat") } )


        }
        rule.onNodeWithText("Fem engasjerte studenter ved Universitetet i Oslo har utviklet en app som vil hjelpe folk å spare penger på strømregningen sin. Appen tar hensyn til værforholdene og sammenligner det med strømprisen gjennom dagen.")
            .assertIsDisplayed()


        //  assertEquals("com.example.team38", appContext.packageName)
    }



}