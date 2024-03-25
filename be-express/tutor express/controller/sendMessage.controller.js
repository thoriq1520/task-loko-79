import KafkaConfig from "../config/kafka.config.js";

const sendMessage = async (req,res)=>{
    try{
        const messages = req.body;
        const kafkaConfig = new KafkaConfig;

        await kafkaConfig.produce("info-loko", messages);

        res.status(200).json({
            status: "OK!",
            message: "Message Successfully send!"
        });
    }catch(err){
        console.log(err);
        res.status(500).json({
            status: "Error",
            message: "Failed to send the message"
        });
    };
}

const controllers = {sendMessage}
export default controllers;