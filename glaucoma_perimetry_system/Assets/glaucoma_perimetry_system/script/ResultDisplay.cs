using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR;
public class ResultDisplay : MonoBehaviour
{
    public Material material;

    private void Start()
    {
        OnPostRender();
    }
    // Update is called once per frame
    void Update()
    {
        
    }
    void OnPostRender()
    {
        //if (SightingPostLocationDeal.count == 72)
        //{
            //XRSettings.enabled = false;
            GL.Clear(true, true, Color.black);
            GL.PushMatrix();
            GL.LoadOrtho();
            material.SetPass(0);
            GL.Begin(GL.QUADS);
            GL.Color(Color.white);
            for (var k = 0; k < SightingPostLocationDeal.sightingPostLocation.Rank; k++)
            {

                GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k,0]/10 + 0.5f, SightingPostLocationDeal.sightingPostLocation[k,1]/10+ 0.5f, 0);

                GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0]/10 + 0.51f, SightingPostLocationDeal.sightingPostLocation[k, 1]/10 + 0.5f, 0);

                GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0]/10 + 0.51f, SightingPostLocationDeal.sightingPostLocation[k, 1]/10 + 0.51f, 0);

                GL.Vertex3(SightingPostLocationDeal.sightingPostLocation[k, 0]/10 + 0.5f, SightingPostLocationDeal.sightingPostLocation[k, 1]/10 + 0.51f, 0);

            }
            GL.End();
            GL.PopMatrix();


        //}
    }
}
