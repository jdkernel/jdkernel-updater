package jdkernel.interfaces;
import jdkernel.customTypes.UpdateInfo;
import jdkernel.interfaces.IDownloadServiceCallback;

interface IDownloadService
{
    void Download(in UpdateInfo ui);
    UpdateInfo getCurrentUpdate();
    String getCurrentMirrorName();
    boolean DownloadRunning();
    void PauseDownload();
    void cancelDownload();
    void registerCallback(in IDownloadServiceCallback cb);
    void unregisterCallback(in IDownloadServiceCallback cb);
}
