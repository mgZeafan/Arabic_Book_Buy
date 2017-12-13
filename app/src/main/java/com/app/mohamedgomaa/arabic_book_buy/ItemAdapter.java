package com.app.mohamedgomaa.arabic_book_buy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder>{
    private ArrayList<item> myList;
    private Context context;

    public ItemAdapter(ArrayList<item> myList, Context myCon) {
        this.myList = myList;
        this.context = myCon;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    static int count = 0;

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Picasso.with(context).load(myList.get(position).pth_photo).error(R.drawable.file_wait).into(holder.img);
        holder.txtPrice.setText(holder.txtPrice.getText()+ String.valueOf(myList.get(position).price));
        if(Locale.getDefault().getLanguage().equals("ar")) {
            holder.txtDetails.setText(myList.get(position).details_ar);
            holder.txtTitle.setText(myList.get(position).title_ar);
            holder.txtAuthor.setText(myList.get(position).author_ar);
        }else {
            holder.txtDetails.setText(myList.get(position).details_en);
            holder.txtTitle.setText(myList.get(position).title_en);
            holder.txtAuthor.setText(myList.get(position).author_en);
        }
        holder.rvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (new CheckConnection_Internet(context).IsConnection()) {
                        File file=mkFolder("Review_" + position + ".pdf");
                        if(!file.exists()) {
                            final Download_file download_file = new Download_file(context,file , position);
                            Initiazation_PBar();
                            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    download_file.cancel(true);
                                }
                            });
                            download_file.execute(myList.get(position).pth_review);
                        }else {
                            OpenFile(position);
                        }
                    }
            }
        });
    }
    ProgressDialog progressDialog;
    void Initiazation_PBar()
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("جارى التحميل .. ");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
    }
    public File mkFolder(String file_name) {
        File  Folder =new File("sdcard/ArabicBook");
        if(!Folder.exists())
        {
            Folder.mkdir();
        }
        File file=new File(Folder,file_name);
        return file;
    }
    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTitle;
        com.borjabravo.readmoretextview.ReadMoreTextView txtDetails;
        TextView txtPrice;
        TextView txtAuthor;
        Button dwn;
        Button rvw;
        CardView cardView;

        public Holder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.imgbook);
            txtTitle = (TextView) view.findViewById(R.id.titleName);
            txtDetails = (ReadMoreTextView) view.findViewById(R.id.details);
            txtPrice = (TextView) view.findViewById(R.id.price);
            txtAuthor=(TextView)view.findViewById(R.id.author);
            dwn=(Button)view.findViewById(R.id.download);
            rvw=(Button)view.findViewById(R.id.review);
            cardView=(CardView)view.findViewById(R.id.card_view);}

    }
    void OpenFile(int position)
    {
        try {
            File file = mkFolder("Review_" + position + ".pdf");
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }catch (Exception e)
        {
            Toast.makeText(context, "download Adobe Reader", Toast.LENGTH_SHORT).show();
        }
    }
    private class  Download_file extends AsyncTask<String,Integer,String>
    {
        int position;
        private Context con;
        private File _file;
        PowerManager.WakeLock myWakeLock;
        public Download_file(Context con,File file,int pos)
        {
            this._file=file;
            this.con = con;
            this.position=pos;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm= (PowerManager) con.getSystemService(con.POWER_SERVICE);
            myWakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,getClass().getName());
            myWakeLock.acquire();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("ok")) {
                OpenFile(position);
                progressDialog.dismiss();

        }
        }
        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream=null;
            OutputStream outputStream=null;
            HttpURLConnection connection=null;
            try {
                URL url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK)
                {
                    return "Server return hTTP "+connection.getResponseCode()+""+connection.getResponseMessage();
                }
                int fileLenght=connection.getContentLength();
                inputStream=connection.getInputStream();
                outputStream=new FileOutputStream(_file);
                byte[]data=new byte[500];
                long total=0;
                int count;
                while ((count=inputStream.read(data))!=-1)
                {
                    total+=count;
                    if(fileLenght>0)
                    {
                        publishProgress((int)(total*100/fileLenght));
                        outputStream.write(data,0,count);
                    }
                }



            } catch (Exception e) {
                return e.getMessage();
            } finally {

                try {
                    if(outputStream!=null)
                        outputStream.close();
                    if (inputStream!=null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(connection!=null)
                {
                    connection.disconnect();
                }


            }
            return "ok";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);
        }
    }
}