package org.energy2d.undo;

import java.awt.Shape;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.energy2d.math.Blob2D;
import org.energy2d.math.TransformableShape;
import org.energy2d.model.Manipulable;
import org.energy2d.model.Model2D;
import org.energy2d.model.Part;
import org.energy2d.view.View2D;

public class UndoScaleManipulable extends AbstractUndoableEdit {

	private static final long serialVersionUID = 1L;
	private Manipulable selectedManipulable;
	private View2D view;
	private Model2D model;
	private String name;
	private float scaleFactor;
	private int scaleDirection;

	public UndoScaleManipulable(View2D view, float scaleFactor, int scaleDirection) {
		this.view = view;
		this.scaleFactor = scaleFactor;
		this.scaleDirection = scaleDirection;
		model = view.getModel();
		selectedManipulable = view.getSelectedManipulable();
		if (selectedManipulable instanceof Part) {
			name = "Part";
		}
	}

	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		if (selectedManipulable instanceof Part) {
			Shape shape = selectedManipulable.getShape();
			if (shape instanceof TransformableShape) {
				TransformableShape s = (TransformableShape) shape;
				switch (scaleDirection) {
				case 0:
					s.scaleX(1.0f / scaleFactor);
					break;
				case 1:
					s.scaleY(1.0f / scaleFactor);
					break;
				case 2:
					s.scale(1.0f / scaleFactor);
					break;
				}
				if (s instanceof Blob2D) {
					((Blob2D) s).update();
				}
				model.refreshPowerArray();
				model.refreshTemperatureBoundaryArray();
				model.refreshMaterialPropertyArrays();
				if (view.isViewFactorLinesOn())
					model.generateViewFactorMesh();
			}
		}
		view.setSelectedManipulable(selectedManipulable);
		view.repaint();
	}

	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		if (selectedManipulable instanceof Part) {
			Shape shape = selectedManipulable.getShape();
			if (shape instanceof TransformableShape) {
				TransformableShape s = (TransformableShape) shape;
				switch (scaleDirection) {
				case 0:
					s.scaleX(scaleFactor);
					break;
				case 1:
					s.scaleY(scaleFactor);
					break;
				case 2:
					s.scale(scaleFactor);
					break;
				}
				if (s instanceof Blob2D) {
					((Blob2D) s).update();
				}
				model.refreshPowerArray();
				model.refreshTemperatureBoundaryArray();
				model.refreshMaterialPropertyArrays();
				if (view.isViewFactorLinesOn())
					model.generateViewFactorMesh();
			}
		}
		view.setSelectedManipulable(selectedManipulable);
		view.repaint();
	}

	@Override
	public String getPresentationName() {
		return "Scale " + (name == null ? "Manipulable" : name);
	}

}
