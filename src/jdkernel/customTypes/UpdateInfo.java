package jdkernel.customTypes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UpdateInfo implements Parcelable, Serializable {
    private static final long serialVersionUID = 5499890003569313403L;

    public int PrimaryKey = -1;
    public final List<String> mod;
    public final List<String> board;
    private String name;
    private String version;
    private String type;
    private String branchCode;
    private List<String> description;
    private String fileName;
    private String versionForApply;
    public List<URI> screenshots;
    public final List<URI> updateMirrors;

    /**
     * Set Name
     */
    public void setName(String _name) {
        if (_name != null)
            name = _name.trim();
        else
            name = "";
    }

    /**
     * Get Name
     * returns INCR: + name, if its an incremental udpate
     */
    public String getName() {
        if (this.isIncremental()) return "INCR: " + name;
        return name;
    }

    /**
     * Set Version
     */
    public void setVersion(String _version) {
        if (_version != null)
            version = _version.trim();
        else
            version = "";
    }

    /**
     * Get Version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set VersionForApply
     */
    public void setVersionForApply(String _version) {
        if (_version != null)
            versionForApply = _version.trim();
        else
            versionForApply = "";
    }

    /**
     * Get VersionForApply
     */
    public String getVersionForApply() {
        return versionForApply;
    }

    /**
     * Set Type
     */
    public void setType(String _type) {
        if (_type != null)
            type = _type.trim();
        else
            type = "";
    }

    /**
     * Get Type
     */
    public String getType() {
        return type;
    }

    /**
     * Set BranchCode
     */
    public void setBranchCode(String _branchCode) {
        if (_branchCode != null)
            branchCode = _branchCode.trim();
        else
            branchCode = "";
    }

    /**
     * Get BranchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Set Descrition
     */
    public void setDescription(String _description) {
        if (_description != null)
            Collections.addAll(description, _description.trim().split("\\|"));
        else
            description = null;
    }

    /**
     * Set Descrition
     */
    public void setDescription(List<String> _description) {
    	description = _description;
    }

    /**
     * Get Description
     */
    public List<String> getDescription() {
        return description;
    }

    /**
     * Set Filename
     */
    public void setFileName(String _fileName) {
        if (_fileName != null)
            fileName = _fileName.trim();
        else
            fileName = "";
    }

    /**
     * Get Filename
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Is this Update for Incremental Updates?
     */
    public Boolean isIncremental() {
        return (this.versionForApply != null && !this.versionForApply.trim().equals(""));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UpdateInfo))
            return false;
        UpdateInfo ui = (UpdateInfo) o;
        //For the old stored updates
        if (ui.screenshots == null)
            ui.screenshots = new LinkedList<URI>();
        if (screenshots == null)
            screenshots = new LinkedList<URI>();
        if (ui.versionForApply == null)
            ui.versionForApply = "";
        if (versionForApply == null)
            versionForApply = "";
        return ui.mod.equals(mod)
                && ui.board.equals(board)
                && ui.name.equals(name)
                && ui.version.equals(version)
                && ui.type.equals(type)
                && ui.branchCode.equals(branchCode)
                && ui.description.equals(description)
                && ui.fileName.equals(fileName)
                && ui.screenshots.equals(screenshots)
                && ui.PrimaryKey == PrimaryKey
                && ui.versionForApply.equals(versionForApply);
    }

    public UpdateInfo() {
        screenshots = new LinkedList<URI>();
        updateMirrors = new LinkedList<URI>();
        description = new LinkedList<String>();
        mod = new LinkedList<String>();
        board = new LinkedList<String>();
    }

    private UpdateInfo(Parcel in) {
        screenshots = new LinkedList<URI>();
        updateMirrors = new LinkedList<URI>();
        description = new LinkedList<String>();
        mod = new LinkedList<String>();
        board = new LinkedList<String>();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<UpdateInfo> CREATOR = new Parcelable.Creator<UpdateInfo>() {
        public UpdateInfo createFromParcel(Parcel in) {
            return new UpdateInfo(in);
        }

        public UpdateInfo[] newArray(int size) {
            return new UpdateInfo[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeInt(PrimaryKey);
        arg0.writeStringList(mod);
        arg0.writeStringList(board);
        arg0.writeString(name);
        arg0.writeString(version);
        arg0.writeString(versionForApply);
        arg0.writeString(type);
        arg0.writeString(branchCode);
        arg0.writeStringList(description);
        arg0.writeString(fileName);
        arg0.writeList(screenshots);
        arg0.writeList(updateMirrors);
    }

    void readFromParcel(Parcel in) {
        PrimaryKey = in.readInt();
        in.readStringList(mod);
        in.readStringList(board);
        name = in.readString();
        version = in.readString();
        versionForApply = in.readString();
        type = in.readString();
        branchCode = in.readString();
        in.readStringList(description);
        fileName = in.readString();
        in.readList(screenshots, URI.class.getClassLoader());
        in.readList(updateMirrors, URI.class.getClassLoader());
    }

    public List<URI> updateFileUris() {
        List<URI> retValue = new LinkedList<URI>();
        for (URI u : updateMirrors) {
            String mirror = u.toString();
            if (!mirror.endsWith("/"))
                mirror += "/";
            retValue.add(URI.create(mirror + fileName));
        }
        return retValue;
    }
}
