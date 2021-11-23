package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.Theme;
import fr.dut.ptut2021.models.ThemeGameCrossRef;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        animationTexte();
        createDatabase();

        new Handler().postDelayed(() -> {
            if (db.appDao().tabUserIsEmpty()) {
                openUserEditPage();
            } else {
                openUserMenuPage();
            }
        }, 1500);
    }

    //TODO Faire bing bing bing bing de la gauche et TADA !
    private void animationTexte() {
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(findViewById(R.id.applicationName));
    }

    private void createDatabase() {
        db = CreateDatabase.getInstance(getApplicationContext());
        createThemes();
        createGames();
        createThemeGamesCross();
    }

    //Here to add/update/delete Theme
    private void createThemes() {
        if (db.appDao().tabThemeIsEmpty()) {
            db.appDao().insertTheme(new Theme("Lettres", R.drawable.lettres));
            db.appDao().insertTheme(new Theme("Chiffres", R.drawable.chiffres));
        }
    }

    //Here to add/update/delete Game
    private void createGames() {
        if (db.appDao().tabGameIsEmpty()) {
            db.appDao().insertGame(new Game("Memory", R.drawable.memory_icon));
            db.appDao().insertGame(new Game("DrawOnIt", R.drawable.memory_icon));
            db.appDao().insertGame(new Game("Syllabe", R.drawable.memory_icon));
            db.appDao().insertGame(new Game("Suite chiffre", R.drawable.memory_icon));
        }
    }

    //Here to set theme to Game
    private void createThemeGamesCross() {
        if (db.appDao().tabThemeGameIsEmpty()) {
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Memory", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Memory", "Chiffres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("DrawOnIt", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("DrawOnIt", "Chiffres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Syllabe", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Suite chiffre", "Chiffres"));
        }
    }

    private void openUserMenuPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
        startActivity(intent);
        finish();
    }

    private void openUserEditPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }
}