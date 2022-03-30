import torch
from PIL import Image
from torchvision.transforms import transforms as transforms


# transform to convert the image to tensor
transform = transforms.Compose([
    transforms.ToTensor()
])

# initialize the model
# model = torchvision.models.detection.keypointrcnn_resnet50_fpn(pretrained=True,
#                                                                num_keypoints=17)

# torch.save(model, '../keypointrcnn.pth')
model = torch.load('../keypointrcnn.pth')


# set the computation device
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
# load the modle on to the computation device and set to eval mode
model.to(device).eval()


image_path = '../input/image1.jpg'
image = Image.open(image_path).convert('RGB')

# # NumPy copy of the image for OpenCV functions
# orig_numpy = np.array(image, dtype=np.float32)
# # convert the NumPy image to OpenCV BGR format
# orig_numpy = cv2.cvtColor(orig_numpy, cv2.COLOR_RGB2BGR) / 255.
# transform the image

image = transform(image)
# add a batch dimension
image = image.unsqueeze(0).to(device)


with torch.no_grad():
    outputs = model(image)

print(outputs)

# output_image = utils.draw_keypoints(outputs, orig_numpy)
# # visualize the image
# cv2.imshow('Keypoint image', output_image)
# cv2.waitKey(0)