package com.modumind.spacex.app.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import com.modumind.spacex.service.SpaceXFacade;
import com.modumind.spacex.service.model.Launch;

public class LaunchPart {

	@Inject
	private SpaceXFacade spaceXFacade;

	private TableViewer launchViewer;

	@PostConstruct
	public void createComposite(Composite parent) {
		createTableViewer(parent);
		
		List<Launch> launches = spaceXFacade.getLaunchService().getLaunches();
		WritableList<Launch> input = new WritableList<Launch>(launches, Launch.class);
		ViewerSupport.bind(launchViewer, input, PojoProperties.values(new String[] { "flightNumber", "missionName" }));
	}

	@Focus
	public void setFocus() {
		launchViewer.getTable().setFocus();
	}

	@Persist
	public void save() {
	}

	private void createTableViewer(Composite parent) {
		launchViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		launchViewer.getTable().setHeaderVisible(true);
		launchViewer.getTable().setLinesVisible(true);

		TableColumn tblColumnDate = new TableColumn(launchViewer.getTable(), SWT.NONE);
		tblColumnDate.setWidth(150);
		tblColumnDate.setText("Flight Number");

		TableColumn tblColumnMsg = new TableColumn(launchViewer.getTable(), SWT.NONE);
		tblColumnMsg.setWidth(300);
		tblColumnMsg.setText("Mission Name");
	}
}