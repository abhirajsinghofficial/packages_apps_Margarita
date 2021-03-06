/*
 * Copyright (C) 2017 Citrus-CAF Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.citrus.theme;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_nav_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.toolbar_item_color, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Hide  title from Expanded toolbar
        CollapsingToolbarLayout cToolbar = findViewById(R.id.collapsing_toolbar_layout);
        cToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        // Close FAB when touched outside
        com.github.clans.fab.FloatingActionMenu fabMain = findViewById(R.id.fab_main);
        fabMain.setClosedOnTouchOutside(true);
        com.github.clans.fab.FloatingActionButton fabApply = findViewById(R.id.fab_apply);
        fabApply.setLabelColors(ContextCompat.getColor(this, R.color.fab_apply_background),
                ContextCompat.getColor(this, R.color.fab_apply_background),
                ContextCompat.getColor(this, R.color.white_transparent));
        fabApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean substratumInstalled = isAppInstalled("projekt.substratum");
                if (substratumInstalled) {
                    Intent intent = new Intent("android.intent.action.MAIN");
                    intent.setComponent(new ComponentName("projekt.substratum","projekt.substratum.LaunchActivity"));

                    startActivity(intent);
                } else {
                    CoordinatorLayout cLayout = findViewById(R.id.app_bar_main_content);
                    Snackbar snackbar = Snackbar
                            .make(cLayout, R.string.substratum_not_installed_message, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.install_message, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.substratum_playstore_link))));
                                }
                            });
                    snackbar.show();
                }
            }
        });


        com.github.clans.fab.FloatingActionButton fabTelegram = findViewById(R.id.fab_telegram);
        fabTelegram.setLabelColors(ContextCompat.getColor(this, R.color.fab_tg_background),
                ContextCompat.getColor(this, R.color.fab_tg_background),
                ContextCompat.getColor(this, R.color.white_transparent));
        fabTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.telegram_channel_link))));
            }
        });

        com.github.clans.fab.FloatingActionButton fabGPlus = findViewById(R.id.fab_gplus);
        fabGPlus.setLabelColors(ContextCompat.getColor(this, R.color.fab_gplus_background),
                ContextCompat.getColor(this, R.color.fab_gplus_background),
                ContextCompat.getColor(this, R.color.white_transparent));
        fabGPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.gplus_community_link))));
            }
        });
        com.github.clans.fab.FloatingActionButton fabGit = findViewById(R.id.fab_git);
        fabGit.setLabelColors(ContextCompat.getColor(this, R.color.fab_git_background),
                ContextCompat.getColor(this, R.color.fab_git_background),
                ContextCompat.getColor(this, R.color.white_transparent));
        fabGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_source_link))));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isAppInstalled(String appUri) {
        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.getPackageInfo(appUri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
