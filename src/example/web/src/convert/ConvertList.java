package example.web.src.convert;

import example.web.src.bean.FileInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConvertList extends Remote{
	public void addQuery(FileInfo fileInfo) throws RemoteException;
}