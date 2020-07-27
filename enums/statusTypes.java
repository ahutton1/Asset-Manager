package enums;
/*  @ahutton1 on github.com
    Software created is for use by University of Rochester Medicine Home Care, and is not for outside use
*/

/*  Enum class that describes different states that assets can be in.
    RETIRED -> Asset is not in use, and will not be used again, however it is not actively being disposed of
    LOANED -> Asset has been given to a user for the day or a set period of time, and is to be returned prior to the time being up
    DISPOSED -> The asset has been either donated or tossed into E-Waste
    ASSIGNED -> A user has bene given the asset to complete their job and daily tasks. The asset is not expected to return unless user's role changes
    DAMAGED -> Damaged hardware that is either awaiting or has been sent out for repairs
    MISSING -> Hardware has either been lost or stolen. If asset is stolen, a report is necessary
    INSTOCK -> Asset is in hands of IT department, and is ready to be, or can be set up to be deployed upon need
*/
public enum statusTypes {
    RETIRED, LOANED, DISPOSED, ASSIGNED, DAMAGED, MISSING, INSTOCK, NONE;
}