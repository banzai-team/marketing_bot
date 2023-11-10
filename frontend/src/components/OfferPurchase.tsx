import React from 'react';
import {CheckCircleIcon, XCircleIcon} from "@heroicons/react/24/outline";

type OfferPurchaseProps = {
    hasOffer: boolean
}

const OfferPurchase: React.FC<OfferPurchaseProps> = ({ hasOffer }) => {
    if (hasOffer) {
        return <CheckCircleIcon className="h-6 w-6 text-success" />;
    }


    return <XCircleIcon className="h-6 w-6 text-error" />;
};

export default OfferPurchase;
