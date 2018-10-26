package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaRest {

        public void crearCategoria(Categoria c) {
            try{
            HttpURLConnection urlConnection;
            DataOutputStream printout;
            InputStream in;

            JSONObject categoriaJson = new JSONObject();
            categoriaJson.put("nombre", c.getNombre());

            URL url = new URL("http://10.0.2.2:5000/categorias");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            printout = new DataOutputStream(urlConnection.getOutputStream());
            String str = categoriaJson.toString();
            byte[] jsonData = str.getBytes("UTF-8");
            printout.write(jsonData);
            printout.flush();

            in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();

            if (urlConnection.getResponseCode() == 200 ||
                    urlConnection.getResponseCode() == 201) {
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                }
                Log.d("LAB_04", sb.toString());
            } else {
                Log.d("Error", "se produjo un error en el proceso!");
            }
                if(printout!=null)  printout.close();
                if(in!=null) in.close();
                if(urlConnection !=null)urlConnection.disconnect();
        } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
    }

    public List<Categoria> listarTodas(){
        List<Categoria> resultado = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        InputStream in =null;

        try{
            URL url = new URL("http://10.0.2.2:5000/categorias");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Type","application/json");
            urlConnection.setRequestMethod("GET");

            // leer respuesta
            in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();

            // si la respuesta es valida, se recolecta la informacion en el StringBuilder (sb)
            if( urlConnection.getResponseCode() ==200 || urlConnection.getResponseCode()==201){
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                }

                // Transformar a JSON
                JSONTokener tokener = new JSONTokener(sb.toString());
                JSONArray listaCategorias = (JSONArray) tokener.nextValue();

                for(int i=0;listaCategorias.length()>=i;i++){
                    String nombre = listaCategorias.getJSONObject(i).getString("nombre");
                    int id = listaCategorias.getJSONObject(i).getInt("id");
                    Categoria cat = new Categoria(id,nombre);
                    resultado.add(cat);
                }
            }else{
                Log.d("Error", "se produjo un error en el proceso!");}
            //cerrando conexion
            if(in!=null) in.close();
            if(urlConnection !=null)urlConnection.disconnect();
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        return resultado;
        }
}
