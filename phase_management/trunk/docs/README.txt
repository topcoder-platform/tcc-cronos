Dear Reviewer,

I made a couple of minor modifications to the originally published API after disucssions with the designer in the
component forum. The return type of getHandlerRegistrationInfo changed from HandlerRegistryInfo to
HandlerRegistryInfo[] and the setPhaseValidator and getPhaseValidator methods were added to the
PhaseManagerinterface.

The component makes use of the Project Phases 2.0 component. Since this component is not yet available, I created a
placeholder component that contains simple implementations of the relevant methods. This component is located in
test_files.

The Project Phases 2.0 has dependencies on the workdays component, so you will need to have this component
installed in addition to the dependencies listed in the component specification.

Sincerely,
T.C. Developer
