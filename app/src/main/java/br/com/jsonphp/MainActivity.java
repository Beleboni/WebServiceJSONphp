package br.com.jsonphp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendJson(View view){

        Carro carro = new Carro();
        carro.setMarca("FIAT");
        carro.setModelo("Palio");
        carro.setPotencias(new ArrayList<Potencia>());
        carro.getPotencias().add(new Potencia(60, 6.0f));
        carro.getPotencias().add(new Potencia(80, 1.5f));
        carro.getPotencias().add(new Potencia(100, 2.0f));

        String json = generateJSON(carro);

        callServer("send-json", json);
    }

    public void getJson(View view){
        callServer("get-json", "");
    }

    public String generateJSON(Carro carro){
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        try{
            jo.put("marca", carro.getMarca());
            jo.put("modelo", carro.getModelo());

            for(int i = 0, tam = carro.getPotencias().size(); i < tam; i++){
                JSONObject aux = new JSONObject();
                aux.put("motor", carro.getPotencias().get(i).getMotor());
                aux.put("cavalos", carro.getPotencias().get(i).getCavalos());
                ja.put(aux);
            }
            jo.put("potencias", ja);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return (jo.toString());
    }

    private Carro degenerateJSON(String data){
        Carro carro = new Carro();

        try{
            JSONObject jo = new JSONObject(data);
            JSONArray ja;

            carro.setMarca(jo.getString("marca"));
            carro.setModelo(jo.getString("modelo"));
            carro.setPotencias(new ArrayList<Potencia>());

            jo.put("marca", carro.getMarca());
            jo.put("modelo", carro.getModelo());

            ja = jo.getJSONArray("potencias");
            for(int i = 0, tam = ja.length(); i < tam; i++){
                Potencia p = new Potencia();
                p.setMotor(ja.getJSONObject(i).getDouble("motor"));
                p.setCavalos(ja.getJSONObject(i).getInt("cavalos"));

                carro.getPotencias().add(p);
            }

            //APRESENTAÇÃO
            Log.i("Script", "Marca: " + carro.getMarca());
            Log.i("Script", "Cavalos: " + carro.getModelo());
            for (int i = 0, tam = carro.getPotencias().size(); i < tam; i++){
                Log.i("Script", "Motor: " + carro.getPotencias().get(i).getMotor());
                Log.i("Script", "Motor: " + carro.getPotencias().get(i).getCavalos());
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return(carro);
    }

    @SuppressLint("NewApi")
    private void callServer(final String method, final String data){
        new Thread(){
            public void run(){
                String answer = HttpConnection.getSetDataWeb("http://www.institutofernandobeleboni.com.br/web-services/service1/process.php", method, data);

                Log.i("Script", "ANSWER: " + answer);

                if(data.isEmpty()){
                    degenerateJSON(answer);
                }
            }

        }.start();
    }
}
