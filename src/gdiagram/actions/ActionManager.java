/**
 * @author Stanko 27.04.2014.
 * ActionManager.java
 */
package gdiagram.actions;

/**
 * @author Stanko
 *
 */
public class ActionManager {
	
	private NewProjectAction newProjectAction;
	private NewDiagramAction  newDiagramAction;
	
	private CascadeWindowAction cascadeDiagramAction;
	private TileVWindowAction tileHorizontallyDiagramAction;
	private TileHWindowAction tileVerticallyDiagramAction;

	
	private NextDiagramAction diagramNextAction;
	private PreviousDiagramAction diagramPreviusAction;
	
	private HelpAboutAction helpAboutAction;
	
	private CloseAllAction closeAllDiagramAction;
	private CloseDiagramAction closeDiagramAction;
	private CloseProjectAction closeProjectAction;
	
	private ExitAction exitAction;
	private DeleteAction deleteAction;
	
	private RenameAction renameAction;
	
	private ExportDiagramAction exportDiagramAction;
	private SaveProjectAction saveProjectAction;
	
	private OpenProjectAction openProjectAction;
	private ImportDiagramAction importDiagramAction;
	
	private StarAction starAction;
	private TriangleAction triangleAction;
	
	private CircleAction circleAction;
	private RectangleAction rectAction;
	private SelectAction selectAction;
	private LinkAction linkAction;
	
	private SelectAllAction selectAllAction;
	
	private SearchAction searchAction;
	
	private ZoomInAction zoomInAction;
	private ZoomOutAction zoomOutAction;
	private BestFitZoomAction bfZoomAction;
	private LassoZoomAction lassoZoomAction;
	
	private UndoAction undoAction;
	private RedoAction redoAction;
	
	private RotateLeftAction rotateLeftAction;
	private RotateRightAction rotateRightAction;
	
	private EditCopyAction copyAction;
	private EditCutAction cutAction;
	private EditPasteAction pasteAction;
	
	public ActionManager(){
		initialize();
	}
	
	public void initialize() {
		newProjectAction=new NewProjectAction();
		newDiagramAction=new NewDiagramAction();
		cascadeDiagramAction=new CascadeWindowAction();
		tileHorizontallyDiagramAction=new TileVWindowAction();
		tileVerticallyDiagramAction=new TileHWindowAction();
    	diagramNextAction=new NextDiagramAction();
		diagramPreviusAction=new PreviousDiagramAction();
		helpAboutAction=new HelpAboutAction();
		closeAllDiagramAction=new CloseAllAction();
		closeDiagramAction=new CloseDiagramAction();
		closeProjectAction=new CloseProjectAction();
		exitAction = new ExitAction();
		deleteAction = new DeleteAction();
		renameAction = new RenameAction();
		openProjectAction =new OpenProjectAction();
		importDiagramAction = new ImportDiagramAction();
     	exportDiagramAction=new ExportDiagramAction();
		saveProjectAction=new SaveProjectAction();
		
		circleAction = new CircleAction();
		rectAction = new RectangleAction();
		starAction = new StarAction();
		linkAction = new LinkAction();
		selectAction = new SelectAction();
		triangleAction = new TriangleAction();
		
		selectAllAction = new SelectAllAction();
		searchAction = new SearchAction();
		
		zoomInAction = new ZoomInAction();
		zoomOutAction = new ZoomOutAction();
		bfZoomAction = new BestFitZoomAction();
		lassoZoomAction = new LassoZoomAction();
		
		undoAction = new UndoAction();
		redoAction = new RedoAction();
		
		rotateLeftAction = new RotateLeftAction();
		rotateRightAction = new RotateRightAction();
		
		copyAction = new EditCopyAction();
		cutAction = new EditCutAction();
		pasteAction = new EditPasteAction();
	}

	/**
	 * @return the newProjectAction
	 */
	public NewProjectAction getNewProjectAction() {
		return newProjectAction;
	}

	/**
	 * @return the newDiagramAction
	 */
	public NewDiagramAction getNewDiagramAction() {
		return newDiagramAction;
	}

	/**
	 * @return the cascadeDiagramAction
	 */
	public CascadeWindowAction getCascadeDiagramAction() {
		return cascadeDiagramAction;
	}

	/**
	 * @return the tileHorizontallyDiagramAction
	 */
	public TileVWindowAction getTileHorizontallyDiagramAction() {
		return tileHorizontallyDiagramAction;
	}

	/**
	 * @return the tileVerticallyDiagramAction
	 */
	public TileHWindowAction getTileVerticallyDiagramAction() {
		return tileVerticallyDiagramAction;
	}

	/**
	 * @return the diagramNextAction
	 */
	public NextDiagramAction getDiagramNextAction() {
		return diagramNextAction;
	}

	/**
	 * @return the diagramPreviusAction
	 */
	public PreviousDiagramAction getDiagramPreviusAction() {
		return diagramPreviusAction;
	}

	/**
	 * @return the helpAboutAction
	 */
	public HelpAboutAction getHelpAboutAction() {
		return helpAboutAction;
	}

	/**
	 * @return the closeAllDiagramAction
	 */
	public CloseAllAction getCloseAllDiagramAction() {
		return closeAllDiagramAction;
	}

	/**
	 * @return the closeDiagramAction
	 */
	public CloseDiagramAction getCloseDiagramAction() {
		return closeDiagramAction;
	}

	/**
	 * @return the closeProjectAction
	 */
	public CloseProjectAction getCloseProjectAction() {
		return closeProjectAction;
	}

	/**
	 * @return the exitAction
	 */
	public ExitAction getExitAction() {
		return exitAction;
	}

	/**
	 * @return the deleteAction
	 */
	public DeleteAction getDeleteAction() {
		return deleteAction;
	}

	/**
	 * @return the renameAction
	 */
	public RenameAction getRenameAction() {
		return renameAction;
	}

	/**
	 * @return the saveDiagramAction
	 */
	public ExportDiagramAction getExportDiagramAction() {
		return exportDiagramAction;
	}

	/**
	 * @return the saveProjectAction
	 */
	public SaveProjectAction getSaveProjectAction() {
		return saveProjectAction;
	}

	/**
	 * @return the openDiagramAction
	 */
	public OpenProjectAction getOpenProjectAction() {
		return openProjectAction;
	}

	/**
	 * @return the starAction
	 */
	public StarAction getStarAction() {
		return starAction;
	}

	/**
	 * @return the triangleAction
	 */
	public TriangleAction getTriangleAction() {
		return triangleAction;
	}

	/**
	 * @return the selectAction
	 */
	public SelectAction getSelectAction() {
		return selectAction;
	}

	/**
	 * @return the linkAction
	 */
	public LinkAction getLinkAction() {
		return linkAction;
	}

	/**
	 * @return the selectAllAction
	 */
	public SelectAllAction getSelectAllAction() {
		return selectAllAction;
	}

	/**
	 * @return the searchAction
	 */
	public SearchAction getSearchAction() {
		return searchAction;
	}

	/**
	 * @return the circleAction
	 */
	public CircleAction getCircleAction() {
		return circleAction;
	}

	/**
	 * @return the rectAction
	 */
	public RectangleAction getRectAction() {
		return rectAction;
	}

	/**
	 * @return the openDiagram
	 */
	public ImportDiagramAction getImportDiagramAction() {
		return importDiagramAction;
	}

	/**
	 * @return the zoomInAction
	 */
	public ZoomInAction getZoomInAction() {
		return zoomInAction;
	}

	/**
	 * @return the zoomOutAction
	 */
	public ZoomOutAction getZoomOutAction() {
		return zoomOutAction;
	}

	/**
	 * @return the bfZoomAction
	 */
	public BestFitZoomAction getBfZoomAction() {
		return bfZoomAction;
	}

	/**
	 * @return the lassoZoomAction
	 */
	public LassoZoomAction getLassoZoomAction() {
		return lassoZoomAction;
	}

	/**
	 * @return the undoAction
	 */
	public UndoAction getUndoAction() {
		return undoAction;
	}

	/**
	 * @return the redoAction
	 */
	public RedoAction getRedoAction() {
		return redoAction;
	}

	/**
	 * @return the rotateLeftAction
	 */
	public RotateLeftAction getRotateLeftAction() {
		return rotateLeftAction;
	}

	/**
	 * @return the rotateRightAction
	 */
	public RotateRightAction getRotateRightAction() {
		return rotateRightAction;
	}

	/**
	 * @return the copyAction
	 */
	public EditCopyAction getCopyAction() {
		return copyAction;
	}

	/**
	 * @return the cutAction
	 */
	public EditCutAction getCutAction() {
		return cutAction;
	}

	/**
	 * @return the pasteAction
	 */
	public EditPasteAction getPasteAction() {
		return pasteAction;
	}

}
