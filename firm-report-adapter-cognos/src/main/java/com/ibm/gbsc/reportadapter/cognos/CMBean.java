package com.ibm.gbsc.reportadapter.cognos;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.rpc.ServiceException;

import org.springframework.stereotype.Component;

import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.ContentManagerServiceStub;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.Graphic;
import com.cognos.developer.schemas.bibus._3.OrderEnum;
import com.cognos.developer.schemas.bibus._3.Output;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.Sort;

@Component
public class CMBean extends BaseBean {
	// private ContentManagerService_PortType cmService;
	private final PropEnum[] properties = { PropEnum.storeID, PropEnum.defaultName, PropEnum.searchPath, PropEnum.objectClass, PropEnum.hasChildren };
	ContentManagerService_ServiceLocator service = new ContentManagerService_ServiceLocator();

	public CMBean() {
	}

	public CognosObject[] searchContent(String searchPath) throws Exception {
		SearchPathMultipleObject cmSearchPath = new SearchPathMultipleObject(searchPath);

		Sort nodeSortType = new Sort();
		Sort nodeSortName = new Sort();

		nodeSortType.setOrder(OrderEnum.ascending);
		nodeSortType.setPropName(PropEnum.objectClass);
		nodeSortName.setOrder(OrderEnum.ascending);
		nodeSortName.setPropName(PropEnum.defaultName);
		Sort[] nodeSorts = { nodeSortType, nodeSortName };

		String appendString = "/*";
		if (searchPath.lastIndexOf("/") == searchPath.length() - 1) {
			appendString = "*";
		}
		if (searchPath.lastIndexOf("*") == searchPath.length() - 1) {
			appendString = "";
		}
		CognosObject[] objs = null;

		cmSearchPath.set_value(cmSearchPath.get_value() + appendString);
		BaseClass[] children = getCmService().query(cmSearchPath, this.properties, nodeSorts, new QueryOptions());
		objs = new CognosObject[children.length];
		int i = 0;
		for (BaseClass b : children) {
			CognosObject obj = new CognosObject();
			obj.setId(b.getStoreID().getValue().get_value());
			obj.setName(b.getDefaultName().getValue().toString());
			obj.setType(b.getObjectClass().getValue().toString());
			obj.setPath(b.getSearchPath().getValue());
			obj.setLeaf(!b.getHasChildren().isValue());
			objs[(i++)] = obj;

			if ((!obj.isLeaf()) && (obj.getType().compareTo("folder") == 0)) {
				obj.setChild(searchContent(obj.getPath()));
			}
		}

		return objs;
	}

	public ContentManagerService_PortType getCmService() throws MalformedURLException, ServiceException {
		ContentManagerService_PortType cmService = service.getcontentManagerService(new URL(getCognosAppUrl()));
		if (getUserName() != null) {
			((ContentManagerServiceStub) cmService).setHeader("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader", getBIBusHeader());
		}
		return cmService;

	}

	public ArrayList<byte[]> getImages(CognosReportOutput reportOutput) throws Exception {
		ArrayList<byte[]> images = new ArrayList<byte[]>();

		if (reportOutput != null && reportOutput.reportOutput.getOutputObjects().length > 0) {
			SearchPathMultipleObject graphicSearchPath = new SearchPathMultipleObject();
			graphicSearchPath.set_value(reportOutput.reportOutput.getOutputObjects()[0].getSearchPath().getValue());

			BaseClass[] bcGraphic = getCmService().query(graphicSearchPath, new PropEnum[] { PropEnum.searchPath }, new Sort[0], new QueryOptions());
			Output out = null;
			if ((bcGraphic.length > 0) && ((bcGraphic[0] instanceof Output))) {
				SearchPathMultipleObject outSearchPath = new SearchPathMultipleObject();
				out = (Output) bcGraphic[0];
				outSearchPath.set_value(out.getSearchPath().getValue() + "/graphic");
				// log.("aaaa=" + out.getSearchPath().getValue() + "/graphic");
				Graphic g = (Graphic) getCmService().query(outSearchPath, new PropEnum[] { PropEnum.searchPath, PropEnum.data, PropEnum.dataType },
						new Sort[0], new QueryOptions())[0];

				images.add(g.getData().getValue());
			}

		}

		return images;
	}

}
