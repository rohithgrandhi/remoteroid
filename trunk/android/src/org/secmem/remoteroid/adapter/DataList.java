package org.secmem.remoteroid.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.secmem.remoteroid.data.ExplorerType;
import org.secmem.remoteroid.data.FileList;
import org.secmem.remoteroid.data.FolderList;
import org.secmem.remoteroid.expinterface.OnFileSelectedListener;
import org.secmem.remoteroid.expinterface.OnPathChangedListener;
import org.secmem.remoteroid.util.HongUtil;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DataList extends ListView {
	
	private ArrayList<ExplorerType> expList = new ArrayList<ExplorerType>();
	private ArrayList<FolderList> folderList = new ArrayList<FolderList>();
	

	private ArrayList<FileList> fileList = new ArrayList<FileList>();
	
//    private ArrayList<String> folderList = new ArrayList<String>();
//    private ArrayList<String> fileList = new ArrayList<String>();
	private ArrayAdapter<String> _Adapter = null; 
	
	// Property 
	private String _Path = "";
	
	// Event
	private OnPathChangedListener onPathChangedListener = null;
	private OnFileSelectedListener onFileSelectedListener = null;


	public DataList(Context context) {
		super(context);
	}
	
	private boolean openPath(String path) {
		folderList.clear();
		fileList.clear();
		
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) return false;
        
        for (int i=0; i<files.length; i++) {
        	if (files[i].isDirectory()) {
        		Log.i("qq","folder");
        		folderList.add(new FolderList(files[i].getName(), ExplorerType.TYPE_FOLDER));
        	} else {
        		Log.i("qq","file");
        		fileList.add(new FileList(files[i].getName(), ExplorerType.TYPE_FILE));
        	}
        }
        
        Collections.sort(folderList, HongUtil.nameComparator);
        Collections.sort(fileList, HongUtil.nameComparator);
        
        folderList.add(0, new FolderList("<..>", ExplorerType.TYPE_FOLDER));
        
        return true;
	}
	
	private void setList() {
		expList.clear();
		expList.addAll(folderList);
		expList.addAll(fileList);
        
	}

	public void setPath(String value) {
		if (value.length() == 0) {
			value = "";
		} else {
			String lastChar = value.substring(value.length()-1, value.length());
			if (lastChar.matches("/") == false) value = value + "/"; 
		}
		
		if (openPath(value)) {
			_Path = value;
			setList();	        
			if (onPathChangedListener != null) onPathChangedListener.onChanged(value);
		}
	}

	public String DelteRight(String value, String border) {
		String list[] = value.split(border);

		String result = "";
		
		for (int i=0; i<list.length; i++) {
			result = result + list[i] + border; 
		}
		
		return result;
	}
	
	private String delteLastFolder(String value) {
		String list[] = value.split("/");

		String result = "";
		
		for (int i=0; i<list.length-1; i++) {
			result = result + list[i] + "/"; 
		}
		
		return result;
	}
	
	public String getRealPathName(String newPath) {
		
		if (newPath.equals("<..>")) {
			return delteLastFolder(_Path);
		} else {
			return _Path + newPath + "/";
		}
	}
	
	
	public String getPath() {
		return _Path;
	}
	
	public void setOnPathChangedListener(OnPathChangedListener value) {
		onPathChangedListener = value;
	}

	public OnPathChangedListener getOnPathChangedListener() {
		return onPathChangedListener;
	}

	public void setOnFileSelected(OnFileSelectedListener value) {
		onFileSelectedListener = value;
	}

	public OnFileSelectedListener getOnFileSelected() {
		return onFileSelectedListener;
	}
	
	public String get_Path() {
		return _Path;
	}

	public void set_Path(String _Path) {
		this._Path = _Path;
	}
	
	public ArrayList<ExplorerType> getExpList() {
		return expList;
	}

	public void setExpList(ArrayList<ExplorerType> expList) {
		this.expList = expList;
	}

	public ArrayList<FolderList> getFolderList() {
		return folderList;
	}

	public void setFolderList(ArrayList<FolderList> folderList) {
		this.folderList = folderList;
	}

	public ArrayList<FileList> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<FileList> fileList) {
		this.fileList = fileList;
	}

}
