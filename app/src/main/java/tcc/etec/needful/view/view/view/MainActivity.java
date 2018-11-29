package tcc.etec.needful.view.view.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.fragments.BloqueadoeCanceladoFragment;
import tcc.etec.needful.view.view.fragments.ChamadosFragment;
import tcc.etec.needful.view.view.fragments.ModeloFragment;
import tcc.etec.needful.view.view.fragments.AgendadosFragment;
import tcc.etec.needful.view.view.fragments.StatusFragment;
import tcc.etec.needful.view.view.fragments.FinalizadosFragment;
import tcc.etec.needful.view.view.model.UsuarioVO;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    ChamadosController controller;
    Context context;
    UsuarioVO tecnico;

    private int idTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tecnico = new UsuarioVO();
        context = getBaseContext();
        controller = new ChamadosController(context);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sincronização do sistema", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        tecnico = (UsuarioVO) intent.getSerializableExtra("tecnico");

        if(intent.getBooleanExtra("sincronizouChamados", true) == false){

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Algo deu errado");
            alertDialog.setMessage("A sincronização de chamados não foi bem sucedida. Tente novamente mais tarde.");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alert = alertDialog.create();
            alert.show();

        }

        if(tecnico == null){
            this.idTecnico = intent.getIntExtra("id_tecnico", 0);
            tecnico = controller.buscarTecnicoPorId(this.idTecnico);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView nomeTecnico = headerView.findViewById(R.id.txt_nome_tecnico);
        TextView emailTecnico = headerView.findViewById(R.id.txt_email_tecnico);

        nomeTecnico.setText(tecnico.getNome());
        emailTecnico.setText(tecnico.getEmail());

        fragmentManager = getSupportFragmentManager();
        Fragment modeloFragment = new ModeloFragment();
        transferirIdTecnico(modeloFragment);

        SharedPreferences prefs = getSharedPreferences("preferenciasUsuario", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("estaLogado", true);
        editor.putInt("id_tecnico",tecnico.getId());
        editor.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sair) {
            SharedPreferences prefs = getSharedPreferences("preferenciasUsuario", 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("estaLogado", false);
            editor.commit();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            setTitle("NeedFul");
            Fragment modeloFragment = new ModeloFragment();
            transferirIdTecnico(modeloFragment);

        } else if (id == R.id.nav_chamados) {

            setTitle("Chamados");
            Fragment chamadosFragment = new ChamadosFragment();
            transferirIdTecnico(chamadosFragment);


        } else if (id == R.id.nav_agendados) {

            setTitle("Agendados");
            Fragment agendadosFragment = new AgendadosFragment();
            transferirIdTecnico(agendadosFragment);

        }else if (id == R.id.nav_bloqueado_cancelado) {

            setTitle("Bloqueados e Cancelados");
            Fragment bloqueadosCancelados = new BloqueadoeCanceladoFragment();
            transferirIdTecnico(bloqueadosCancelados);

        } else if (id == R.id.nav_finalizados) {

            setTitle("Chamados Finalizados");
            Fragment finalizadosFragment = new FinalizadosFragment();
            transferirIdTecnico(finalizadosFragment);

        } else if (id == R.id.nav_status) {

            setTitle("Status");
            Fragment statusFragment = new StatusFragment();
            transferirIdTecnico(statusFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void transferirIdTecnico(Fragment tela) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_tecnico", this.idTecnico);
        tela.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_fragment, tela).commit();
    }
}
