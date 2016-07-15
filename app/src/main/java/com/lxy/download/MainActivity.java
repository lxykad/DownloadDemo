package com.lxy.download;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ProgressBar mProgressBar2;
    private ArrayList<String> mUrlList;
    private int mDownloadId1;

    private final FileDownloadListener mDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            int progress = soFarBytes * 100 / totalBytes;
            mProgressBar.setProgress(progress);
        }

        @Override
        protected void completed(BaseDownloadTask task) {

        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {

        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progress_bar2);

        mUrlList = new ArrayList<>();
        mUrlList.add(HttpHelper.URL1);
        mUrlList.add(HttpHelper.URL2);


    }


    //暂停下载
    public void downloadPause(View view) {
        Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
        FileDownloader.getImpl().pause(mDownloadId1);
    }

    //开始下载1
    public void downloadStart(View view) {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        mDownloadId1 = createDownloadTask(HttpHelper.URL1, "test1.m4a").start();
    }


    //取消下载
    public void downloadCancel(View view) {
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        File file = new File(Environment.getExternalStorageDirectory() + "/DownloadFile/" + "test1.m4a");
        file.delete();
    }


    //暂停下载2
    public void downloadPause2(View view) {
        Toast.makeText(this, "pause2", Toast.LENGTH_SHORT).show();
    }

    //开始下载2
    public void downloadStart2(View view) {
        Toast.makeText(this, "start2", Toast.LENGTH_SHORT).show();

    }

    //取消下载2
    public void downloadCancel2(View view) {
        Toast.makeText(this, "cancel2", Toast.LENGTH_SHORT).show();
    }

    public void startAll(View view) {
        Toast.makeText(this, "startAll", Toast.LENGTH_SHORT).show();
        FileDownloadQueueSet queueSet = new FileDownloadQueueSet(mDownloadListener);
        ArrayList<BaseDownloadTask> tasks = new ArrayList<>();

        for (int i = 0; i < mUrlList.size(); i++) {
            tasks.add(FileDownloader.getImpl().create(mUrlList.get(i)));
        }
        // 由于是队列任务, 这里是我们假设了现在不需要每个任务都回调`FileDownloadList
        queueSet.disableCallbackProgressTimes();
        // 所有任务在下载失败的时候都自动重试一次
        queueSet.setAutoRetryTimes(1);

        queueSet.downloadTogether(tasks);

    }

    //创建下载任务
    public BaseDownloadTask createDownloadTask(String url, String fileName) {
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(url)
                .setPath(Environment.getExternalStorageDirectory() + "/DownloadFile/" + fileName)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(mDownloadListener);


        return baseDownloadTask;
    }

    //检查更新
    public void checkUpdate(View view) {
        Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();

    }

}
